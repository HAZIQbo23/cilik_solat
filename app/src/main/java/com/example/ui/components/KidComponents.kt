package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CharacterType
import com.example.data.model.RukunCategory
import com.example.ui.theme.*

@Composable
fun KidTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    starsCount: Int? = null,
    characterType: CharacterType? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(4.dp, CircleShape)
                            .background(Color.White, CircleShape)
                            .testTag("btn_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = SkyBlueDark
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }

                if (characterType != null) {
                    Image(
                        painter = painterResource(id = characterType.drawableRes),
                        contentDescription = characterType.displayName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(GoldLight, CircleShape)
                            .testTag("avatar_topbar")
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = SkyBlueDark,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1
                )
            }

            if (starsCount != null) {
                StarCounterBadge(
                    stars = starsCount,
                    modifier = Modifier.testTag("stars_badge_top")
                )
            }
        }
    }
}

@Composable
fun StarCounterBadge(
    stars: Int,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "star_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = GoldLight,
        border = BorderStroke(2.dp, GoldPrimary),
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Bintang",
                tint = GoldPrimary,
                modifier = Modifier
                    .size(20.dp)
                    .scale(scale)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$stars",
                color = GoldDark,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun KidButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    backgroundColor: Color = SkyBluePrimary,
    contentColor: Color = Color.White,
    borderColor: Color = SkyBlueDark,
    enabled: Boolean = true,
    testTag: String = "kid_button"
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        border = BorderStroke(2.5.dp, borderColor),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp)
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = contentColor
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = contentColor
            )
        }
    }
}

@Composable
fun KidCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    borderColor: Color = SkyBluePrimary.copy(alpha = 0.3f),
    elevation: Int = 4,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardModifier = if (onClick != null) {
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
    } else {
        modifier.fillMaxWidth()
    }

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(2.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun SpeechBubble(
    text: String,
    modifier: Modifier = Modifier,
    bubbleColor: Color = Color.White,
    textColor: Color = TextDark
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = bubbleColor,
        border = BorderStroke(2.dp, SkyBluePrimary.copy(alpha = 0.4f)),
        shadowElevation = 3.dp,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun CategoryBadge(
    category: RukunCategory,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (category) {
        RukunCategory.QALBI -> CategoryQalbi.copy(alpha = 0.15f) to CategoryQalbi
        RukunCategory.FI_LI -> CategoryFiLi.copy(alpha = 0.15f) to CategoryFiLi
        RukunCategory.QAULI -> CategoryQauli.copy(alpha = 0.15f) to CategoryQauli
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        modifier = modifier
    ) {
        Text(
            text = category.title,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
