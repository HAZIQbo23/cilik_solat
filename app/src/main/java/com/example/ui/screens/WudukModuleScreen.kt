package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CharacterType
import com.example.data.model.WudukStep
import com.example.data.repository.SolatDataRepository
import com.example.ui.components.KidButton
import com.example.ui.components.KidCard
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun WudukModuleScreen(
    characterType: CharacterType,
    starsCount: Int,
    completedSteps: Set<Int>,
    onStepCompleted: (Int) -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val steps = SolatDataRepository.wudukSteps
    var currentStepIndex by remember { mutableIntStateOf(0) }
    val currentStep = steps[currentStepIndex]
    val isCompleted = completedSteps.contains(currentStep.stepNumber)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Jom Belajar Wuduk!",
                onBackClick = onBackClick,
                characterType = characterType,
                starsCount = starsCount
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 10.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            if (currentStepIndex > 0) {
                                currentStepIndex--
                                audioHelper.playWaterSplashSound()
                            }
                        },
                        enabled = currentStepIndex > 0,
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(2.dp, SkyBluePrimary),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_wuduk_prev")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sebelumnya", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (currentStepIndex < steps.size - 1) {
                                currentStepIndex++
                                audioHelper.playWaterSplashSound()
                            }
                        },
                        enabled = currentStepIndex < steps.size - 1,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_wuduk_next")
                    ) {
                        Text("Seterusnya", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Step Number Pills Selector
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(steps) { step ->
                    val isCurrent = step.stepNumber == currentStep.stepNumber
                    val isDone = completedSteps.contains(step.stepNumber)

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = when {
                            isCurrent -> SkyBluePrimary
                            isDone -> EmeraldLight
                            else -> Color.White
                        },
                        border = BorderStroke(
                            2.dp,
                            if (isCurrent) SkyBlueDark else if (isDone) EmeraldPrimary else Color.LightGray
                        ),
                        modifier = Modifier
                            .clickable {
                                currentStepIndex = step.stepNumber - 1
                                audioHelper.playWaterSplashSound()
                            }
                            .testTag("step_chip_${step.stepNumber}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "${step.stepNumber}",
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isCurrent) Color.White else if (isDone) EmeraldDark else TextDark,
                                fontSize = 14.sp
                            )
                            if (isDone) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Selesai",
                                    tint = EmeraldDark,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main Step Presentation Card
            KidCard(
                backgroundColor = SkyBlueLight,
                borderColor = SkyBluePrimary,
                modifier = Modifier.testTag("wuduk_content_card")
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SkyBluePrimary
                        ) {
                            Text(
                                text = "Langkah ${currentStep.stepNumber} daripada ${steps.size}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        if (currentStep.isSunat) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = GoldLight,
                                border = BorderStroke(1.dp, GoldPrimary)
                            ) {
                                Text(
                                    text = "Sunat Wuduk ⭐",
                                    color = GoldDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        } else {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = EmeraldLight,
                                border = BorderStroke(1.dp, EmeraldPrimary)
                            ) {
                                Text(
                                    text = "Rukun Wuduk (Wajib) ✓",
                                    color = EmeraldDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentStep.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = SkyBlueDark,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Character visual bubble
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = characterType.drawableRes),
                                contentDescription = characterType.displayName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(SkyBlueLight)
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            // Arabic recitation
                            Text(
                                text = currentStep.arabicText,
                                style = ArabicLargeStyle,
                                color = TextDark,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = currentStep.transliteration,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = SkyBlueDark,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "\"${currentStep.translation}\"",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Normal,
                                color = TextMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Kid friendly instructions & tips
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = BorderStroke(1.5.dp, SkyBluePrimary.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("💡", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = currentStep.kidTips,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextDark,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Audio play & Mark done buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                audioHelper.playWaterSplashSound()
                                audioHelper.speak("${currentStep.title}. ${currentStep.transliteration}. Maksudnya: ${currentStep.translation}")
                            },
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("btn_listen_wuduk")
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Dengar (Audio)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Button(
                            onClick = {
                                audioHelper.playStarChime()
                                onStepCompleted(currentStep.stepNumber)
                            },
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCompleted) EmeraldPrimary else GoldPrimary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("btn_complete_wuduk")
                        ) {
                            Icon(
                                if (isCompleted) Icons.Default.CheckCircle else Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isCompleted) "Dah Faham! ✓" else "Faham! (+3⭐)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
