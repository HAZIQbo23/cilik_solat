package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BacaanSolat
import com.example.data.model.CharacterType
import com.example.data.repository.SolatDataRepository
import com.example.ui.components.KidCard
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun BacaanSolatScreen(
    characterType: CharacterType,
    starsCount: Int,
    masteredBacaanIds: Set<Int>,
    onMasteredBacaan: (Int) -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val bacaanList = SolatDataRepository.bacaanSolatList
    var selectedBacaan by remember { mutableStateOf<BacaanSolat?>(null) }
    var isSlowMode by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Jom Belajar Bacaan Solat!",
                onBackClick = onBackClick,
                characterType = characterType,
                starsCount = starsCount
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Header Info Pill
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = KidPurpleLight,
                border = BorderStroke(1.5.dp, KidPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📖", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Lafaz & Bacaan Solat",
                            fontWeight = FontWeight.Black,
                            color = KidPurple,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Pilih mana-mana bacaan di bawah untuk mendengar sebutan & maknanya!",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextDark
                        )
                    }
                }
            }

            // List of Bacaan Items
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp, top = 4.dp)
            ) {
                items(bacaanList) { item ->
                    val isMastered = masteredBacaanIds.contains(item.id)

                    KidCard(
                        backgroundColor = if (isMastered) EmeraldLight.copy(alpha = 0.6f) else Color.White,
                        borderColor = if (isMastered) EmeraldPrimary else KidPurple.copy(alpha = 0.3f),
                        onClick = {
                            selectedBacaan = item
                            audioHelper.playCorrectSound()
                        },
                        modifier = Modifier.testTag("bacaan_item_${item.id}")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = if (isMastered) EmeraldPrimary else KidPurple,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${item.number}",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = item.transliteration.take(40) + "...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMuted
                                )
                            }

                            IconButton(
                                onClick = {
                                    audioHelper.speak(item.arabicText)
                                },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(KidPurpleLight, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Dengar",
                                    tint = KidPurple,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal BottomSheet / Dialog when a Bacaan is selected
    if (selectedBacaan != null) {
        val current = selectedBacaan!!
        val isMastered = masteredBacaanIds.contains(current.id)

        AlertDialog(
            onDismissRequest = {
                audioHelper.stopSpeech()
                selectedBacaan = null
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = Color.White,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = current.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = KidPurple
                    )
                    IconButton(
                        onClick = {
                            audioHelper.stopSpeech()
                            selectedBacaan = null
                        }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup")
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Arabic recitation
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = CloudBackground,
                        border = BorderStroke(1.5.dp, SkyBluePrimary.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = current.arabicText,
                            style = ArabicLargeStyle,
                            color = TextDark,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(14.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = current.transliteration,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = SkyBlueDark,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "\"${current.meaning}\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = GoldLight,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "💡 Panduan: ${current.tips}",
                            style = MaterialTheme.typography.bodySmall,
                            color = GoldDark,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Speed toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Kelajuan: ",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                        FilterChip(
                            selected = !isSlowMode,
                            onClick = { isSlowMode = false },
                            label = { Text("Biasa") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = isSlowMode,
                            onClick = { isSlowMode = true },
                            label = { Text("Perlahan 🐢") }
                        )
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            audioHelper.speak(
                                text = "${current.transliteration}. Maksudnya: ${current.meaning}",
                                isSlow = isSlowMode
                            )
                        },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidPurple),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_dialog_play_audio")
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Dengar", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            audioHelper.playStarChime()
                            onMasteredBacaan(current.id)
                        },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isMastered) EmeraldPrimary else GoldPrimary
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_dialog_master_bacaan")
                    ) {
                        Icon(
                            if (isMastered) Icons.Default.CheckCircle else Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isMastered) "Dah Hafal!" else "Hafal! (+3⭐)",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        )
    }
}
