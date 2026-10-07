package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KidButton
import com.example.ui.components.KidCard
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun SettingsScreen(
    musicEnabled: Boolean,
    sfxEnabled: Boolean,
    isEnglish: Boolean,
    onToggleMusic: (Boolean) -> Unit,
    onToggleSfx: (Boolean) -> Unit,
    onToggleLanguage: (Boolean) -> Unit,
    onResetProgress: () -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    var showResetDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Tetapan",
                onBackClick = onBackClick
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
                        .padding(16.dp)
                ) {
                    KidButton(
                        text = "Kembali ke Menu",
                        onClick = onBackClick,
                        backgroundColor = SkyBluePrimary,
                        borderColor = SkyBlueDark,
                        modifier = Modifier.testTag("btn_back_settings")
                    )
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Audio Controls Card
            KidCard(
                backgroundColor = Color.White,
                borderColor = SkyBluePrimary.copy(alpha = 0.4f)
            ) {
                Column {
                    Text(
                        text = "🔊 Kawalan Audio & Bunyi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SkyBlueDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Music Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MusicNote, contentDescription = null, tint = SkyBluePrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Muzik Latar",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Switch(
                            checked = musicEnabled,
                            onCheckedChange = {
                                audioHelper.playCorrectSound()
                                onToggleMusic(it)
                            },
                            modifier = Modifier.testTag("switch_music")
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // SFX Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VolumeUp, contentDescription = null, tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Kesan Bunyi (SFX)",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Switch(
                            checked = sfxEnabled,
                            onCheckedChange = {
                                audioHelper.playCorrectSound()
                                onToggleSfx(it)
                            },
                            modifier = Modifier.testTag("switch_sfx")
                        )
                    }
                }
            }

            // Language Card
            KidCard(
                backgroundColor = Color.White,
                borderColor = KidPurple.copy(alpha = 0.4f)
            ) {
                Column {
                    Text(
                        text = "🌐 Pilihan Bahasa",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = KidPurple
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Translate, contentDescription = null, tint = KidPurple)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Bahasa Antara Muka",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            FilterChip(
                                selected = !isEnglish,
                                onClick = { onToggleLanguage(false) },
                                label = { Text("BM") },
                                modifier = Modifier.testTag("chip_lang_bm")
                            )
                            FilterChip(
                                selected = isEnglish,
                                onClick = { onToggleLanguage(true) },
                                label = { Text("English") },
                                modifier = Modifier.testTag("chip_lang_en")
                            )
                        }
                    }
                }
            }

            // Reset Progress Card
            KidCard(
                backgroundColor = KidPinkLight.copy(alpha = 0.5f),
                borderColor = KidPink
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Reset Kemajuan",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = KidPink
                        )
                        Text(
                            text = "Mulakan semula semua bintang dan kuiz dari awal.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextDark
                        )
                    }

                    Button(
                        onClick = { showResetDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = KidPink),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.testTag("btn_trigger_reset")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset")
                    }
                }
            }

            // About & Islamic Curriculum Info
            KidCard(
                backgroundColor = EmeraldLight.copy(alpha = 0.5f),
                borderColor = EmeraldPrimary
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = EmeraldDark)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mengenai Cilik Solat",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Aplikasi pembelajaran interaktif solat dan wuduk untuk kanak-kanak berteraskan sukatan Pendidikan Islam (Mazhab Syafi'i). Sesuai untuk bimbingan ibu bapa dan guru.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Versi 1.0 • Cilik Solat Studio",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }
        }
    }

    // Reset Confirmation Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            shape = RoundedCornerShape(24.dp),
            title = {
                Text("Adakah anda pasti?", fontWeight = FontWeight.Bold, color = KidPink)
            },
            text = {
                Text("Semua rekod bintang, lencana, dan kemajuan pembelajaran akan dipadamkan semula ke paras asal.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        audioHelper.playWrongSound()
                        onResetProgress()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KidPink),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Ya, Padam Semua")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}
