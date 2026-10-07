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
import androidx.compose.material.icons.filled.VolumeUp
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CharacterType
import com.example.ui.components.KidButton
import com.example.ui.components.KidTopBar
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun CharacterSelectScreen(
    selectedCharacter: CharacterType,
    onSelectCharacter: (CharacterType) -> Unit,
    onProceed: () -> Unit,
    onBackClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CloudBackground)
            .navigationBarsPadding()
    ) {
        KidTopBar(
            title = "Pilih Watak",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Pilih Kawan Anda! 🌟",
                    style = MaterialTheme.typography.headlineMedium,
                    color = SkyBlueDark,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Jom belajar solat bersama saya!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                )

                // 2 Characters Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Ahmad Card
                    CharacterCard(
                        character = CharacterType.AHMAD,
                        isSelected = selectedCharacter == CharacterType.AHMAD,
                        accentColor = EmeraldPrimary,
                        lightAccent = EmeraldLight,
                        onSelect = {
                            audioHelper.playCorrectSound()
                            audioHelper.speak(CharacterType.AHMAD.introSpeech)
                            onSelectCharacter(CharacterType.AHMAD)
                        },
                        onPlayVoice = {
                            audioHelper.speak(CharacterType.AHMAD.introSpeech)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    // Aisyah Card
                    CharacterCard(
                        character = CharacterType.AISYAH,
                        isSelected = selectedCharacter == CharacterType.AISYAH,
                        accentColor = KidPink,
                        lightAccent = KidPinkLight,
                        onSelect = {
                            audioHelper.playCorrectSound()
                            audioHelper.speak(CharacterType.AISYAH.introSpeech)
                            onSelectCharacter(CharacterType.AISYAH)
                        },
                        onPlayVoice = {
                            audioHelper.speak(CharacterType.AISYAH.introSpeech)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Proceed
            KidButton(
                text = "Mula Belajar dengan ${selectedCharacter.displayName}! ✨",
                onClick = {
                    audioHelper.playCorrectSound()
                    onProceed()
                },
                backgroundColor = GoldPrimary,
                borderColor = GoldDark,
                modifier = Modifier.testTag("btn_confirm_character")
            )
        }
    }
}

@Composable
private fun CharacterCard(
    character: CharacterType,
    isSelected: Boolean,
    accentColor: Color,
    lightAccent: Color,
    onSelect: () -> Unit,
    onPlayVoice: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) lightAccent else Color.White
        ),
        border = BorderStroke(
            width = if (isSelected) 3.5.dp else 1.5.dp,
            color = if (isSelected) accentColor else Color.LightGray.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 3.dp
        ),
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onSelect)
            .testTag("char_card_${character.name.lowercase()}")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(14.dp)
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                Image(
                    painter = painterResource(id = character.drawableRes),
                    contentDescription = character.displayName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .shadow(4.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(accentColor, CircleShape)
                            .shadow(2.dp, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Dipilih",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = character.displayName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Text(
                text = character.greeting,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = accentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Audio Greeting Button
            OutlinedButton(
                onClick = onPlayVoice,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, accentColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = accentColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .heightIn(min = 40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Dengar Suara",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Suara",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = onSelect,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) accentColor else Color(0xFFF1F5F9),
                    contentColor = if (isSelected) Color.White else TextDark
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp)
            ) {
                Text(
                    text = if (isSelected) "Dipilih ✓" else "Pilih",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
