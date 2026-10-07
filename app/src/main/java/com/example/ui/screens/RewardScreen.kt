package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CharacterType
import com.example.data.repository.SolatDataRepository
import com.example.ui.components.KidButton
import com.example.ui.components.KidCard
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun RewardScreen(
    characterType: CharacterType,
    starsCount: Int,
    unlockedBadges: Set<String>,
    onBackToMenu: () -> Unit,
    onPlayAgain: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val badges = SolatDataRepository.allBadges

    LaunchedEffect(Unit) {
        audioHelper.playCheerFanfare()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "trophy_scale")
    val scaleTrophy by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Sistem Ganjaran",
                onBackClick = onBackToMenu,
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            audioHelper.playCorrectSound()
                            onBackToMenu()
                        },
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(2.dp, SkyBluePrimary),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_back_menu_reward")
                    ) {
                        Icon(Icons.Default.Home, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Menu Utama", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            audioHelper.playCorrectSound()
                            onPlayAgain()
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_play_again_reward")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Main Lagi", fontWeight = FontWeight.Bold)
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
            // Big Celebration Card
            KidCard(
                backgroundColor = GoldLight,
                borderColor = GoldPrimary,
                modifier = Modifier.testTag("celebration_card")
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "🏆",
                        fontSize = 54.sp,
                        modifier = Modifier.scale(scaleTrophy)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Tahniah!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = GoldDark
                    )

                    Text(
                        text = "Anda telah mengumpul",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextDark
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$starsCount Bintang!",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            color = GoldDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Character Praise Speech
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = characterType.drawableRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(GoldLight)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = BorderStroke(2.dp, SkyBluePrimary.copy(alpha = 0.4f)),
                    shadowElevation = 3.dp,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Syabas adik hebat! Teruskan istiqamah belajar dan mendirikan solat ya! 🕌",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Badges Section
            Text(
                text = "Lencana Diperoleh 🏅",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = SkyBlueDark,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            badges.forEach { badge ->
                val isUnlocked = unlockedBadges.contains(badge.id)

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isUnlocked) EmeraldLight else Color.White
                    ),
                    border = BorderStroke(
                        2.dp,
                        if (isUnlocked) EmeraldPrimary else Color.LightGray.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .testTag("badge_item_${badge.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isUnlocked) Color.White else Color(0xFFF1F5F9),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = if (isUnlocked) badge.iconEmoji else "🔒",
                                    fontSize = 24.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = badge.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isUnlocked) EmeraldDark else TextDark
                            )
                            Text(
                                text = badge.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted
                            )
                        }

                        if (isUnlocked) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = EmeraldPrimary
                            ) {
                                Text(
                                    text = "Dibuka ✓",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
