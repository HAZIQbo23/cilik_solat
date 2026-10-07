package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CharacterType
import com.example.data.repository.SolatDataRepository
import com.example.ui.components.KidCard
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun SolatWaktuScreen(
    characterType: CharacterType,
    starsCount: Int,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val waktuList = SolatDataRepository.solatWaktuList

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "Solat 5 Waktu & Niat",
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
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = KidOrangeLight,
                border = BorderStroke(1.5.dp, KidOrange),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🕌", fontSize = 26.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Ketahui nama solat fardu, bilangan rakaat dan lafaz niatnya!",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextDark,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(waktuList) { item ->
                    KidCard(
                        backgroundColor = Color.White,
                        borderColor = SkyBluePrimary.copy(alpha = 0.4f),
                        modifier = Modifier.testTag("solat_waktu_${item.name.lowercase().replace(" ", "_")}")
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = SkyBluePrimary,
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "${item.id}",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = SkyBlueDark
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = GoldLight,
                                    border = BorderStroke(1.dp, GoldPrimary)
                                ) {
                                    Text(
                                        text = "${item.rakaat} Rakaat",
                                        fontWeight = FontWeight.Bold,
                                        color = GoldDark,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Text(
                                text = "⏰ ${item.timeDescription}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = CloudBackground,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = item.niatArab,
                                        style = ArabicMediumStyle,
                                        color = TextDark,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = item.niatRumi,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = SkyBlueDark,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "\"${item.niatMaksud}\"",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = {
                                        audioHelper.speak("${item.name}, ${item.rakaat} rakaat. Niatnya: ${item.niatRumi}. Maksud: ${item.niatMaksud}")
                                    },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(SkyBlueLight, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "Dengar",
                                        tint = SkyBluePrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
