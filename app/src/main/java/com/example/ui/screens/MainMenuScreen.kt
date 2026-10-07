package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.CharacterType
import com.example.ui.components.KidTopBar
import com.example.ui.components.SpeechBubble
import com.example.ui.theme.*
import com.example.util.AudioHelper

data class MenuModuleItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconEmoji: String,
    val primaryColor: Color,
    val lightColor: Color,
    val progressText: String,
    val onClick: () -> Unit
)

@Composable
fun MainMenuScreen(
    studentName: String,
    characterType: CharacterType,
    starsCount: Int,
    completedWudukCount: Int,
    masteredRukunCount: Int,
    masteredBacaanCount: Int,
    quizHighScore: Int,
    onNavigateWuduk: () -> Unit,
    onNavigateRukun: () -> Unit,
    onNavigateBacaan: () -> Unit,
    onNavigateQuiz: () -> Unit,
    onNavigateMiniGame: () -> Unit,
    onNavigateSolatWaktu: () -> Unit,
    onNavigateReward: () -> Unit,
    onNavigateProfile: () -> Unit,
    onNavigateSettings: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val modules = listOf(
        MenuModuleItem(
            id = "modul_wuduk",
            title = "Modul Wuduk",
            subtitle = "Langkah tertib wuduk suci",
            iconEmoji = "💧",
            primaryColor = SkyBluePrimary,
            lightColor = SkyBlueLight,
            progressText = "$completedWudukCount/8 Langkah",
            onClick = {
                audioHelper.playWaterSplashSound()
                onNavigateWuduk()
            }
        ),
        MenuModuleItem(
            id = "modul_rukun",
            title = "Rukun Solat",
            subtitle = "13 Rukun solat sempurna",
            iconEmoji = "🕌",
            primaryColor = GoldPrimary,
            lightColor = GoldLight,
            progressText = "$masteredRukunCount/13 Rukun",
            onClick = {
                audioHelper.playCorrectSound()
                onNavigateRukun()
            }
        ),
        MenuModuleItem(
            id = "modul_bacaan",
            title = "Bacaan Solat",
            subtitle = "Lafaz Arab, rumi & terjemahan",
            iconEmoji = "📖",
            primaryColor = KidPurple,
            lightColor = KidPurpleLight,
            progressText = "$masteredBacaanCount/11 Bacaan",
            onClick = {
                audioHelper.playCorrectSound()
                onNavigateBacaan()
            }
        ),
        MenuModuleItem(
            id = "modul_kuiz",
            title = "Kuiz Hebat",
            subtitle = "Uji minda & kumpul bintang!",
            iconEmoji = "⭐",
            primaryColor = KidPink,
            lightColor = KidPinkLight,
            progressText = "Rekod: $quizHighScore Markah",
            onClick = {
                audioHelper.playStarChime()
                onNavigateQuiz()
            }
        ),
        MenuModuleItem(
            id = "modul_susun",
            title = "Susun Tertib Solat",
            subtitle = "Mini game susun rukun & simulasi!",
            iconEmoji = "🧩",
            primaryColor = EmeraldPrimary,
            lightColor = EmeraldLight,
            progressText = "Permainan Kanak-Kanak",
            onClick = {
                audioHelper.playCorrectSound()
                onNavigateMiniGame()
            }
        ),
        MenuModuleItem(
            id = "modul_waktu",
            title = "Solat 5 Waktu",
            subtitle = "Bilangan rakaat & lafaz niat",
            iconEmoji = "⏰",
            primaryColor = KidOrange,
            lightColor = KidOrangeLight,
            progressText = "Subuh, Zohor, Asar, Maghrib, Isyak",
            onClick = {
                audioHelper.playCorrectSound()
                onNavigateSolatWaktu()
            }
        )
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Cilik Solat",
                characterType = characterType,
                starsCount = starsCount
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 12.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateReward,
                        modifier = Modifier
                            .size(52.dp)
                            .testTag("nav_reward")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Ganjaran",
                                tint = GoldPrimary,
                                modifier = Modifier.size(26.dp)
                            )
                            Text("Ganjaran", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldDark)
                        }
                    }

                    IconButton(
                        onClick = onNavigateMiniGame,
                        modifier = Modifier
                            .size(52.dp)
                            .testTag("nav_minigame")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Extension,
                                contentDescription = "Mini Game",
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(26.dp)
                            )
                            Text("Puzzle", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        }
                    }

                    IconButton(
                        onClick = onNavigateProfile,
                        modifier = Modifier
                            .size(52.dp)
                            .testTag("nav_profile")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profil",
                                tint = SkyBluePrimary,
                                modifier = Modifier.size(26.dp)
                            )
                            Text("Profil", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark)
                        }
                    }

                    IconButton(
                        onClick = onNavigateSettings,
                        modifier = Modifier
                            .size(52.dp)
                            .testTag("nav_settings")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Tetapan",
                                tint = TextMuted,
                                modifier = Modifier.size(26.dp)
                            )
                            Text("Tetapan", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Character greeting banner
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, SkyBluePrimary.copy(alpha = 0.25f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        audioHelper.speak("Assalamualaikum adik $studentName! Jom kita mulakan belajar!")
                    }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = characterType.drawableRes),
                        contentDescription = characterType.displayName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(GoldLight)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Hai Adik $studentName! 👋",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = SkyBlueDark
                        )
                        Text(
                            text = "Hari ini kita nak belajar apa ya? Tekan mana-mana kotak di bawah!",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextDark,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    IconButton(
                        onClick = {
                            audioHelper.speak("Solat itu tiang agama. Mari pelajari wuduk dan rukun solat bersama!")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Bunyi",
                            tint = SkyBluePrimary
                        )
                    }
                }
            }

            // Grid of learning modules
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 16.dp, top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(modules) { item ->
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = item.lightColor),
                        border = BorderStroke(2.5.dp, item.primaryColor.copy(alpha = 0.7f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .clickable(onClick = item.onClick)
                            .testTag("menu_card_${item.id}")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.iconEmoji,
                                    fontSize = 32.sp
                                )
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.9f),
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = null,
                                            tint = item.primaryColor,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            Column {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = TextDark,
                                    maxLines = 1
                                )
                                Text(
                                    text = item.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextDark.copy(alpha = 0.8f),
                                    maxLines = 2,
                                    lineHeight = 14.sp
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = item.progressText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = item.primaryColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
