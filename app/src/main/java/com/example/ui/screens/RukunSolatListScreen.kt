package com.example.ui.screens

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.ui.components.KidButton
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun RukunSolatListScreen(
    characterType: CharacterType,
    starsCount: Int,
    masteredRukunIds: Set<Int>,
    onSelectRukun: (Int) -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val rukunList = SolatDataRepository.rukunSolatList
    val progressFraction = (masteredRukunIds.size.toFloat() / rukunList.size.toFloat()).coerceIn(0f, 1f)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CloudBackground,
        topBar = {
            KidTopBar(
                title = "13 Rukun Solat",
                onBackClick = onBackClick,
                characterType = characterType,
                starsCount = starsCount
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KidButton(
                        text = "▶ Mula Belajar Rukun 1 (Niat)",
                        onClick = {
                            audioHelper.playCorrectSound()
                            onSelectRukun(1)
                        },
                        icon = Icons.Default.PlayArrow,
                        backgroundColor = EmeraldPrimary,
                        borderColor = EmeraldDark,
                        modifier = Modifier.testTag("btn_start_rukun_1")
                    )
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
            // Header Hero Banner
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = GoldLight),
                border = BorderStroke(2.dp, GoldPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = characterType.drawableRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Jom Belajar 13 Rukun Solat! 🕌",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldDark
                        )
                        Text(
                            text = "Solat itu tiang agama, kunci 13 rukun yang betul!",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = EmeraldPrimary,
                            trackColor = Color.White
                        )
                        Text(
                            text = "${masteredRukunIds.size}/13 Rukun Dikuasai",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }

            // Grid of 13 Rukun Cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 6.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(rukunList) { rukun ->
                    val isMastered = masteredRukunIds.contains(rukun.id)

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isMastered) EmeraldLight else Color.White
                        ),
                        border = BorderStroke(
                            2.dp,
                            if (isMastered) EmeraldPrimary else SkyBluePrimary.copy(alpha = 0.35f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {
                                audioHelper.playCorrectSound()
                                onSelectRukun(rukun.id)
                            }
                            .testTag("rukun_item_${rukun.id}")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isMastered) EmeraldPrimary else SkyBluePrimary,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${rukun.number}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 13.sp
                                        )
                                    }
                                }

                                if (isMastered) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Dikuasai",
                                        tint = EmeraldPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Text(
                                text = rukun.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextDark,
                                maxLines = 2,
                                lineHeight = 16.sp
                            )

                            CategoryBadge(category = rukun.category)
                        }
                    }
                }
            }
        }
    }
}
