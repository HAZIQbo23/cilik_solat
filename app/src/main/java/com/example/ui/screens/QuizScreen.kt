package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CharacterType
import com.example.data.model.QuizQuestion
import com.example.data.repository.SolatDataRepository
import com.example.ui.components.KidButton
import com.example.ui.components.KidCard
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(
    characterType: CharacterType,
    starsCount: Int,
    onQuizCompleted: (score: Int) -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val questions = remember { SolatDataRepository.quizQuestions.shuffled().take(5) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(100) }
    var timerSeconds by remember { mutableIntStateOf(25) }
    var isTimerRunning by remember { mutableStateOf(true) }

    val currentQuestion = questions.getOrNull(currentIndex)

    // Countdown Timer effect
    LaunchedEffect(currentIndex, isTimerRunning) {
        timerSeconds = 25
        while (isTimerRunning && timerSeconds > 0 && !isAnswerSubmitted) {
            delay(1000)
            timerSeconds--
            if (timerSeconds == 0 && !isAnswerSubmitted) {
                // Auto submit when time's up
                isAnswerSubmitted = true
                audioHelper.playWrongSound()
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Kuiz Hebat Solat!",
                onBackClick = onBackClick,
                characterType = characterType,
                starsCount = starsCount
            )
        }
    ) { innerPadding ->
        if (currentQuestion == null) {
            // Completed
            LaunchedEffect(Unit) {
                onQuizCompleted(score)
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = GoldPrimary)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar Stats (Score & Timer)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = GoldLight,
                    border = BorderStroke(2.dp, GoldPrimary)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Markah: $score",
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldDark,
                            fontSize = 14.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (timerSeconds <= 5) KidPinkLight else SkyBlueLight,
                    border = BorderStroke(2.dp, if (timerSeconds <= 5) KidPink else SkyBluePrimary)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = if (timerSeconds <= 5) KidPink else SkyBluePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "00:${timerSeconds.toString().padStart(2, '0')}",
                            fontWeight = FontWeight.ExtraBold,
                            color = if (timerSeconds <= 5) KidPink else SkyBlueDark,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Question Box
            KidCard(
                backgroundColor = Color.White,
                borderColor = KidPink,
                modifier = Modifier.testTag("quiz_card_main")
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = KidPinkLight
                    ) {
                        Text(
                            text = "Soalan ${currentIndex + 1} daripada ${questions.size}",
                            color = KidPink,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = currentQuestion.questionText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = TextDark,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Options list (A, B, C)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val labels = listOf("A", "B", "C")
                val optionColors = listOf(EmeraldPrimary, SkyBluePrimary, KidPurple)

                currentQuestion.options.forEachIndexed { idx, optionText ->
                    val isSelected = selectedOptionIndex == idx
                    val isCorrect = idx == currentQuestion.correctIndex

                    val cardBg = when {
                        isAnswerSubmitted && isCorrect -> EmeraldLight
                        isAnswerSubmitted && isSelected && !isCorrect -> KidPinkLight
                        isSelected -> GoldLight
                        else -> Color.White
                    }

                    val borderColor = when {
                        isAnswerSubmitted && isCorrect -> EmeraldPrimary
                        isAnswerSubmitted && isSelected && !isCorrect -> KidPink
                        isSelected -> GoldPrimary
                        else -> Color.LightGray.copy(alpha = 0.6f)
                    }

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        border = BorderStroke(2.dp, borderColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(enabled = !isAnswerSubmitted) {
                                selectedOptionIndex = idx
                                audioHelper.playCorrectSound()
                            }
                            .testTag("quiz_option_$idx")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = if (isSelected || (isAnswerSubmitted && isCorrect)) optionColors[idx % 3] else Color(0xFFF1F5F9),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = labels[idx],
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 16.sp,
                                        color = if (isSelected || (isAnswerSubmitted && isCorrect)) Color.White else TextDark
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextDark,
                                modifier = Modifier.weight(1f)
                            )

                            if (isAnswerSubmitted) {
                                if (isCorrect) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Betul", tint = EmeraldPrimary)
                                } else if (isSelected) {
                                    Icon(Icons.Default.Close, contentDescription = "Salah", tint = KidPink)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Explanation & Result banner if submitted
            AnimatedVisibility(visible = isAnswerSubmitted) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = if (selectedOptionIndex == currentQuestion.correctIndex) EmeraldLight else KidPinkLight,
                    border = BorderStroke(
                        2.dp,
                        if (selectedOptionIndex == currentQuestion.correctIndex) EmeraldPrimary else KidPink
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (selectedOptionIndex == currentQuestion.correctIndex) "🎉 Syabas, Betul!" else "💡 Jangan Putus Asa!",
                            fontWeight = FontWeight.Black,
                            color = if (selectedOptionIndex == currentQuestion.correctIndex) EmeraldDark else KidPink,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentQuestion.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextDark
                        )
                    }
                }
            }

            // Bottom Character cheer & Action Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = characterType.drawableRes),
                    contentDescription = characterType.displayName,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(GoldLight)
                )

                Spacer(modifier = Modifier.width(12.dp))

                if (!isAnswerSubmitted) {
                    KidButton(
                        text = "Hantar Jawapan ✍️",
                        onClick = {
                            if (selectedOptionIndex != null) {
                                isAnswerSubmitted = true
                                val correct = selectedOptionIndex == currentQuestion.correctIndex
                                if (correct) {
                                    audioHelper.playCorrectSound()
                                } else {
                                    audioHelper.playWrongSound()
                                    score = (score - 20).coerceAtLeast(0)
                                }
                            }
                        },
                        enabled = selectedOptionIndex != null,
                        backgroundColor = KidPink,
                        borderColor = Color(0xFFBE123C),
                        modifier = Modifier.weight(1f).testTag("btn_submit_quiz")
                    )
                } else {
                    KidButton(
                        text = if (currentIndex < questions.size - 1) "Soalan Seterusnya ➡️" else "Lihat Keputusan 🏆",
                        onClick = {
                            if (currentIndex < questions.size - 1) {
                                currentIndex++
                                selectedOptionIndex = null
                                isAnswerSubmitted = false
                            } else {
                                onQuizCompleted(score)
                            }
                        },
                        backgroundColor = EmeraldPrimary,
                        borderColor = EmeraldDark,
                        modifier = Modifier.weight(1f).testTag("btn_next_quiz")
                    )
                }
            }
        }
    }
}
