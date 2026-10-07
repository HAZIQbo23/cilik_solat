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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CharacterType
import com.example.data.model.RukunSolat
import com.example.data.repository.SolatDataRepository
import com.example.ui.components.KidButton
import com.example.ui.components.KidCard
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

enum class GameTab {
    SUSUN_TERTIB,
    SIMULASI_SOLAT
}

@Composable
fun MiniGameSolatScreen(
    characterType: CharacterType,
    starsCount: Int,
    onEarnStars: (Int) -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(GameTab.SUSUN_TERTIB) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Kuiz Mini & Simulasi Solat",
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
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tab Selector (Susun Tertib vs Simulasi Solat)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        audioHelper.playCorrectSound()
                        selectedTab = GameTab.SUSUN_TERTIB
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == GameTab.SUSUN_TERTIB) EmeraldPrimary else Color.White,
                        contentColor = if (selectedTab == GameTab.SUSUN_TERTIB) Color.White else TextDark
                    ),
                    border = BorderStroke(2.dp, EmeraldPrimary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🧩 Susun Tertib", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        audioHelper.playCorrectSound()
                        selectedTab = GameTab.SIMULASI_SOLAT
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == GameTab.SIMULASI_SOLAT) SkyBluePrimary else Color.White,
                        contentColor = if (selectedTab == GameTab.SIMULASI_SOLAT) Color.White else TextDark
                    ),
                    border = BorderStroke(2.dp, SkyBluePrimary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🕌 Simulasi Solat", fontWeight = FontWeight.Bold)
                }
            }

            AnimatedContent(
                targetState = selectedTab,
                label = "tab_switch"
            ) { tab ->
                when (tab) {
                    GameTab.SUSUN_TERTIB -> SusunTertibGame(
                        characterType = characterType,
                        audioHelper = audioHelper,
                        onEarnStars = onEarnStars
                    )
                    GameTab.SIMULASI_SOLAT -> SimulasiSolatGuide(
                        characterType = characterType,
                        audioHelper = audioHelper,
                        onEarnStars = onEarnStars
                    )
                }
            }
        }
    }
}

@Composable
private fun SusunTertibGame(
    characterType: CharacterType,
    audioHelper: AudioHelper,
    onEarnStars: (Int) -> Unit
) {
    // A subset of 5 key sequential rukun to test kids
    val fullSteps = remember {
        listOf(
            1 to "Niat",
            2 to "Takbiratul Ihram",
            4 to "Membaca Al-Fatihah",
            5 to "Rukuk serta Thoma'ninah",
            7 to "Sujud serta Thoma'ninah"
        )
    }

    var shuffledSteps by remember { mutableStateOf(fullSteps.shuffled()) }
    var userSequence by remember { mutableStateOf<List<Pair<Int, String>>>(emptyList()) }
    var isWon by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Game Instruction Card
        KidCard(
            backgroundColor = EmeraldLight,
            borderColor = EmeraldPrimary
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🧩", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Permainan Susun Tertib Solat",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                    Text(
                        text = "Tekan kad mengikut urutan rukun solat dari awal hingga akhir!",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextDark
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Chosen sequence slots
        Text(
            text = "Urutan Pilihan Anda (${userSequence.size}/5):",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (i in 0 until 5) {
                val item = userSequence.getOrNull(i)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (item != null) SkyBlueLight else Color.White,
                    border = BorderStroke(1.5.dp, if (item != null) SkyBluePrimary else Color.LightGray),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        if (item != null) {
                            Text(
                                text = "${i + 1}. ${item.second}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SkyBlueDark,
                                textAlign = TextAlign.Center,
                                lineHeight = 12.sp
                            )
                        } else {
                            Text(
                                text = "${i + 1}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Available choice buttons
        Text(
            text = "Pilih Rukun Seterusnya:",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            shuffledSteps.forEach { step ->
                val isSelected = userSequence.any { it.first == step.first }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Color(0xFFF1F5F9) else Color.White
                    ),
                    border = BorderStroke(
                        1.5.dp,
                        if (isSelected) Color.LightGray else EmeraldPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(enabled = !isSelected && !isWon) {
                            val nextSequence = userSequence + step
                            userSequence = nextSequence

                            // Check correct so far
                            val isCorrectOrder = nextSequence.map { it.first } == fullSteps.take(nextSequence.size).map { it.first }
                            if (isCorrectOrder) {
                                audioHelper.playCorrectSound()
                                if (nextSequence.size == 5) {
                                    isWon = true
                                    audioHelper.playCheerFanfare()
                                    onEarnStars(10)
                                }
                            } else {
                                audioHelper.playWrongSound()
                                // Wrong step picked, rollback last pick
                                userSequence = userSequence.dropLast(1)
                            }
                        }
                        .testTag("step_pick_${step.first}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isSelected) Color.LightGray else EmeraldLight,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (isSelected) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(20.dp))
                                } else {
                                    Text("👉", fontSize = 16.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = step.second,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) TextMuted else TextDark,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Win state card
        if (isWon) {
            KidCard(
                backgroundColor = GoldLight,
                borderColor = GoldPrimary
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("🎉 TAHNIAH!", fontSize = 24.sp, fontWeight = FontWeight.Black, color = GoldDark)
                    Text("Adik telah menyusun rukun solat dengan tertib yang sempurna! (+10 ⭐)", textAlign = TextAlign.Center, color = TextDark)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            userSequence = emptyList()
                            shuffledSteps = fullSteps.shuffled()
                            isWon = false
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Main Semula")
                    }
                }
            }
        } else {
            OutlinedButton(
                onClick = {
                    userSequence = emptyList()
                    shuffledSteps = fullSteps.shuffled()
                },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, SkyBluePrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Set Semula Pilihan")
            }
        }
    }
}

@Composable
private fun SimulasiSolatGuide(
    characterType: CharacterType,
    audioHelper: AudioHelper,
    onEarnStars: (Int) -> Unit
) {
    val solatSteps = remember {
        listOf(
            "1. Niat & Berdiri Betul" to "Usolli fardhas Subhi rak'ataini lillahi ta'ala.",
            "2. Takbiratul Ihram" to "Allahu Akbar (Angkat kedua belah tangan setinggi telinga).",
            "3. Membaca Al-Fatihah" to "Bismillahir Rahmanir Rahim... waladh-dhallin. Amin.",
            "4. Rukuk serta Thoma'ninah" to "Subhana Rabbiyal 'Azimi wa bihamdih (3x).",
            "5. I'tidal & Doa Qunut" to "Sami'allahu liman hamidah... Allahummahdini fiman hadayt.",
            "6. Sujud Pertama" to "Subhana Rabbiyal A'la wa bihamdih (3x).",
            "7. Duduk Antara Dua Sujud" to "Rabbighfirli warhamni wajburni...",
            "8. Sujud Kedua" to "Subhana Rabbiyal A'la wa bihamdih (3x).",
            "9. Tahiyyat Akhir & Selawat" to "At-tahiyyatul mubarakatus salawatut tayyibatu lillah...",
            "10. Memberi Salam" to "Assalamu'alaikum warahmatullah (Paling ke kanan)."
        )
    }

    var currentStepIdx by remember { mutableIntStateOf(0) }
    val step = solatSteps[currentStepIdx]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KidCard(
            backgroundColor = SkyBlueLight,
            borderColor = SkyBluePrimary
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Surface(shape = RoundedCornerShape(12.dp), color = SkyBluePrimary) {
                    Text(
                        text = "Simulasi Solat Subuh (Langkah ${currentStepIdx + 1}/10)",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    painter = painterResource(id = characterType.drawableRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = step.first,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = SkyBlueDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = step.second,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        audioHelper.speak("${step.first}. ${step.second}")
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                ) {
                    Icon(Icons.Default.VolumeUp, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Dengar Bacaan")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    if (currentStepIdx > 0) currentStepIdx--
                },
                enabled = currentStepIdx > 0,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("Sebelumnya")
            }

            Button(
                onClick = {
                    if (currentStepIdx < solatSteps.size - 1) {
                        currentStepIdx++
                        audioHelper.playCorrectSound()
                    } else {
                        audioHelper.playCheerFanfare()
                        onEarnStars(10)
                    }
                },
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentStepIdx == solatSteps.size - 1) EmeraldPrimary else GoldPrimary
                ),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text(
                    text = if (currentStepIdx == solatSteps.size - 1) "Selesai Solat! 🌟" else "Seterusnya 👉",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
