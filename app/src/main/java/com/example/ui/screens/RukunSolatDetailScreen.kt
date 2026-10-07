package com.example.ui.screens

import androidx.compose.animation.core.*
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.example.data.model.RukunSolat
import com.example.data.repository.SolatDataRepository
import com.example.ui.components.CategoryBadge
import com.example.ui.components.KidCard
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun RukunSolatDetailScreen(
    rukunId: Int,
    characterType: CharacterType,
    starsCount: Int,
    isMastered: Boolean,
    onMastered: (Int) -> Unit,
    onNavigateNext: (Int) -> Unit,
    onNavigatePrev: (Int) -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val rukunList = SolatDataRepository.rukunSolatList
    val currentRukun = rukunList.find { it.id == rukunId } ?: rukunList.first()

    var characterBounce by remember { mutableStateOf(false) }
    val charScale by animateFloatAsState(
        targetValue = if (characterBounce) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        finishedListener = { characterBounce = false },
        label = "char_bounce"
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Rukun ${currentRukun.number}/13",
                onBackClick = onBackClick,
                characterType = characterType,
                starsCount = starsCount
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
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
                            if (currentRukun.id > 1) {
                                audioHelper.playCorrectSound()
                                onNavigatePrev(currentRukun.id - 1)
                            }
                        },
                        enabled = currentRukun.id > 1,
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(2.dp, SkyBluePrimary),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_rukun_prev")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sebelumnya", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (currentRukun.id < 13) {
                                audioHelper.playCorrectSound()
                                onNavigateNext(currentRukun.id + 1)
                            }
                        },
                        enabled = currentRukun.id < 13,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_rukun_next")
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title Header & Category Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryBadge(category = currentRukun.category)

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SkyBlueLight
                ) {
                    Text(
                        text = "Rukun ke-${currentRukun.number}",
                        fontWeight = FontWeight.Bold,
                        color = SkyBlueDark,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${currentRukun.number}. ${currentRukun.name}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = SkyBlueDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Character posture interactive card
            KidCard(
                backgroundColor = Color.White,
                borderColor = GoldPrimary,
                modifier = Modifier.testTag("rukun_character_card")
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .scale(charScale)
                            .clip(CircleShape)
                            .clickable {
                                characterBounce = true
                                audioHelper.playCorrectSound()
                                audioHelper.speak("Rukun ke-${currentRukun.number}, ${currentRukun.name}. ${currentRukun.kidGuide}")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = characterType.drawableRes),
                            contentDescription = characterType.displayName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(110.dp)
                                .shadow(6.dp, CircleShape)
                                .clip(CircleShape)
                                .background(GoldLight)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = GoldLight,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "👆 Tekan pada watak untuk ulang sebutan!",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Arabic Text Box
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = CloudBackground,
                        border = BorderStroke(1.5.dp, SkyBluePrimary.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(14.dp)
                        ) {
                            Text(
                                text = currentRukun.arabicLafaz,
                                style = ArabicLargeStyle,
                                color = TextDark,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = currentRukun.transliteration,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = SkyBlueDark,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"${currentRukun.meaning}\"",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Movement & Kid Guidelines
            KidCard(
                backgroundColor = EmeraldLight,
                borderColor = EmeraldPrimary
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = EmeraldDark,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Panduan Perbuatan Kanak-Kanak",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = currentRukun.kidGuide,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "⭐ Tips: ${currentRukun.motionTips}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = EmeraldDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Audio recitation button & Mastered button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        audioHelper.speak(
                            "${currentRukun.name}. ${currentRukun.transliteration}. Maksudnya: ${currentRukun.meaning}."
                        )
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("btn_listen_rukun")
                ) {
                    Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Lafaz (Audio)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        audioHelper.playStarChime()
                        onMastered(currentRukun.id)
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isMastered) EmeraldPrimary else GoldPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("btn_master_rukun")
                ) {
                    Icon(
                        if (isMastered) Icons.Default.CheckCircle else Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isMastered) "Dah Kuasai! ✓" else "Kuasai! (+3⭐)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
