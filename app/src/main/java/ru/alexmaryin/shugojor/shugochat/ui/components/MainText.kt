package ru.alexmaryin.shugojor.shugochat.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ru.alexmaryin.shugojor.shugochat.ui.theme.textUnderline

@Composable
fun MainText(
    text: String,
    isUnderlined: Boolean = false,
    onClick: () -> Unit = {}
) {

    val modifier = Modifier
        .padding(horizontal = 5.dp)
        .drawBehind {
            if (isUnderlined) drawLine(
                color = textUnderline,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = Stroke.DefaultMiter,
                cap = StrokeCap.Round
            )
        }
        .clickable(isUnderlined) { onClick() }

    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.button
    )
}