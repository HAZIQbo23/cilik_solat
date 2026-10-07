package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.KidButton
import com.example.ui.theme.*
import com.example.util.AudioHelper

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    audioHelper: AudioHelper,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_start")
    val bounceAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFBAE6FD),
                        Color(0xFFE0F2FE),
                        Color(0xFFF0FDF4)
                    )
                )
            )
    ) {
        // Mosque background illustration
        Image(
            painter = painterResource(id = R.drawable.bg_cilik_mosque),
            contentDescription = "Masjid Cilik",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.48f)
                .align(Alignment.TopCenter)
        )

        // Gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.3f to Color.Transparent,
                        0.55f to Color.White.copy(alpha = 0.88f),
                        0.8f to Color.White
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Title Card
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .testTag("homescreen_header_card")
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Cilik Solat",
                            style = MaterialTheme.typography.headlineLarge,
                            color = SkyBlueDark,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "Jom Belajar Solat & Wuduk!",
                        style = MaterialTheme.typography.titleMedium,
                        color = GoldDark,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Characters showcase (Ahmad & Aisyah)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ahmad
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.char_ahmad),
                        contentDescription = "Ahmad",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .shadow(6.dp, CircleShape)
                            .clip(CircleShape)
                            .background(EmeraldLight)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Ahmad",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = EmeraldDark
                    )
                }

                // Aisyah
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.char_aisyah),
                        contentDescription = "Aisyah",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .shadow(6.dp, CircleShape)
                            .clip(CircleShape)
                            .background(KidPinkLight)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Aisyah",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = KidPink
                    )
                }
            }

            // Welcome Text & Start Action
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SkyBlueLight,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "✨ Selamat Datang ke Cilik Solat! ✨\nBelajar solat menjadi mudah dan seronok!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = SkyBlueDark,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                    )
                }

                KidButton(
                    text = "Mula Belajar Sekarang! 🚀",
                    onClick = {
                        audioHelper.playCorrectSound()
                        onStartClick()
                    },
                    icon = Icons.Default.PlayArrow,
                    backgroundColor = GoldPrimary,
                    borderColor = GoldDark,
                    modifier = Modifier
                        .scale(bounceAnim)
                        .testTag("btn_start_learning")
                )
            }
        }
    }
}
