package ru.alexmaryin.shugojor.shugochat.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import ru.alexmaryin.shugojor.shugochat.ui.theme.darkBackground
import ru.alexmaryin.shugojor.shugochat.ui.theme.onPrimary
import ru.alexmaryin.shugojor.shugochat.ui.theme.primary
import ru.alexmaryin.shugojor.shugochat.ui.theme.textSecondary

@Composable
fun PowerButton(
    isEnabled: Boolean = true,
    isAnimated: Boolean = false,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    var sizeInitial by remember { mutableStateOf(100.dp) }
    val buttonSize by animateDpAsState(
        targetValue = sizeInitial,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    var backgroundInitial by remember { mutableStateOf(primary) }
    val background by animateColorAsState(
        targetValue = backgroundInitial,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(key1 = isAnimated) {
        if (isAnimated) {
            sizeInitial = 95.dp
            backgroundInitial = darkBackground
        }
    }

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(20.dp)
            .size(100.dp)
            .shadow(10.dp, RoundedCornerShape(50), true)
            .background(color = if (isAnimated) background else primary),
        enabled = isEnabled
    ) {
        Icon(
            imageVector = Icons.Default.PowerSettingsNew,
            contentDescription = contentDescription,
            modifier = Modifier.size(if (isAnimated) buttonSize else 100.dp),
            tint = if (isEnabled) onPrimary else textSecondary
        )
    }
}