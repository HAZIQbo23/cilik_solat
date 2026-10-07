package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
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
fun ProfileScreen(
    studentName: String,
    characterType: CharacterType,
    starsCount: Int,
    unlockedBadges: Set<String>,
    completedWudukCount: Int,
    masteredRukunCount: Int,
    masteredBacaanCount: Int,
    dailySolatChecks: Set<String>,
    onUpdateName: (String) -> Unit,
    onSwitchCharacter: (CharacterType) -> Unit,
    onToggleDailySolat: (String) -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    var showEditNameDialog by remember { mutableStateOf(false) }
    var inputName by remember { mutableStateOf(studentName) }

    // Overall progress calculation: (wuduk / 8 * 30%) + (rukun / 13 * 40%) + (bacaan / 11 * 30%)
    val progressOverall = (
        (completedWudukCount.toFloat() / 8f * 0.3f) +
        (masteredRukunCount.toFloat() / 13f * 0.4f) +
        (masteredBacaanCount.toFloat() / 11f * 0.3f)
    ).coerceIn(0f, 1f)
    val progressPercent = (progressOverall * 100).toInt()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Profil Pelajar",
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
                        .padding(16.dp)
                ) {
                    KidButton(
                        text = "Kembali ke Menu",
                        onClick = onBackClick,
                        backgroundColor = SkyBluePrimary,
                        borderColor = SkyBlueDark,
                        modifier = Modifier.testTag("btn_back_profile")
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Card (Name, Avatar, Stars)
            KidCard(
                backgroundColor = Color.White,
                borderColor = SkyBluePrimary,
                modifier = Modifier.testTag("profile_card_main")
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Image(
                            painter = painterResource(id = characterType.drawableRes),
                            contentDescription = characterType.displayName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(96.dp)
                                .shadow(6.dp, CircleShape)
                                .clip(CircleShape)
                                .background(GoldLight)
                        )

                        IconButton(
                            onClick = {
                                val nextChar = if (characterType == CharacterType.AHMAD) CharacterType.AISYAH else CharacterType.AHMAD
                                audioHelper.playCorrectSound()
                                onSwitchCharacter(nextChar)
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .background(SkyBluePrimary, CircleShape)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Tukar Watak", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { showEditNameDialog = true }
                    ) {
                        Text(
                            text = studentName,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            color = SkyBlueDark
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.Default.Edit, contentDescription = "Ubah Nama", tint = SkyBluePrimary, modifier = Modifier.size(18.dp))
                    }

                    Text(
                        text = "Watak Kawan: ${characterType.displayName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = GoldLight,
                        border = BorderStroke(1.5.dp, GoldPrimary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Jumlah Bintang: $starsCount",
                                fontWeight = FontWeight.ExtraBold,
                                color = GoldDark,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Overall Progress Bar Card
            KidCard(
                backgroundColor = Color.White,
                borderColor = EmeraldPrimary
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Kemajuan Keseluruhan",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextDark
                        )
                        Text(
                            text = "$progressPercent%",
                            fontWeight = FontWeight.Black,
                            style = MaterialTheme.typography.titleMedium,
                            color = EmeraldDark
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { progressOverall },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        color = EmeraldPrimary,
                        trackColor = Color(0xFFE2E8F0)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("💧 Wuduk: $completedWudukCount/8", fontSize = 12.sp, color = TextMuted)
                        Text("🕌 Rukun: $masteredRukunCount/13", fontSize = 12.sp, color = TextMuted)
                        Text("📖 Bacaan: $masteredBacaanCount/11", fontSize = 12.sp, color = TextMuted)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checklist Solat 5 Waktu Hari Ini (Enhancement!)
            KidCard(
                backgroundColor = GoldLight.copy(alpha = 0.5f),
                borderColor = GoldPrimary
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⏰", fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Checklist Solat 5 Waktu Hari Ini",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark
                        )
                    }
                    Text(
                        text = "Tanda solat yang telah adik tunaikan untuk dapat +5⭐ setiap waktu!",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextDark,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val solatNames = listOf("Subuh", "Zohor", "Asar", "Maghrib", "Isyak")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        solatNames.forEach { name ->
                            val isChecked = dailySolatChecks.contains(name)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        audioHelper.playStarChime()
                                        onToggleDailySolat(name)
                                    }
                                    .padding(4.dp)
                                    .testTag("chk_solat_$name")
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isChecked) EmeraldPrimary else Color.White,
                                    border = BorderStroke(2.dp, if (isChecked) EmeraldDark else Color.LightGray),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        if (isChecked) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isChecked) EmeraldDark else TextDark
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lencana Dikumpul Showcase
            Text(
                text = "Lencana Dikumpul (${unlockedBadges.size}/${SolatDataRepository.allBadges.size}) 🏅",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = SkyBlueDark,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SolatDataRepository.allBadges.forEach { badge ->
                    val isUnlocked = unlockedBadges.contains(badge.id)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isUnlocked) EmeraldLight else Color(0xFFF1F5F9),
                        border = BorderStroke(1.5.dp, if (isUnlocked) EmeraldPrimary else Color.LightGray),
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (isUnlocked) badge.iconEmoji else "🔒",
                                fontSize = 22.sp
                            )
                            Text(
                                text = badge.title.take(6),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isUnlocked) EmeraldDark else TextMuted
                            )
                        }
                    }
                }
            }
        }
    }

    // Edit Name Dialog
    if (showEditNameDialog) {
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            shape = RoundedCornerShape(24.dp),
            title = {
                Text("Tukar Nama Pelajar", fontWeight = FontWeight.Bold)
            },
            text = {
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Nama Anda") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().testTag("input_edit_name")
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateName(inputName)
                        showEditNameDialog = false
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditNameDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}
