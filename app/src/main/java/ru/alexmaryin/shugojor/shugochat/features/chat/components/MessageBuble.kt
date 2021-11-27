package ru.alexmaryin.shugojor.shugochat.features.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.alexmaryin.shugojor.shugochat.features.chat.model.Message
import ru.alexmaryin.shugojor.shugochat.ui.theme.activeSurface
import ru.alexmaryin.shugojor.shugochat.ui.theme.primary

@Composable
fun MessageBubble(message: Message, isOwn: Boolean = false) {
    Box(
        contentAlignment = if (isOwn) Alignment.CenterEnd else Alignment.CenterStart,
        modifier = Modifier.fillMaxWidth()
    ) {
        val cornerRadius = 6.dp
        Column(
            modifier = Modifier
                .width(300.dp)
                .drawBehind {
                    val triangleHeight = 15.dp.toPx()
                    val triangleWidth = 20.dp.toPx()
                    val trianglePath = Path().apply {
                        if (isOwn) {
                            moveTo(size.width, size.height - cornerRadius.toPx())
                            lineTo(size.width, size.height + triangleHeight)
                            lineTo(size.width - triangleWidth, size.height - cornerRadius.toPx())
                            close()
                        } else {
                            moveTo(0f, size.height - cornerRadius.toPx())
                            lineTo(0f, size.height + triangleHeight)
                            lineTo(triangleWidth, size.height - cornerRadius.toPx())
                            close()
                        }
                    }
                    drawPath(
                        path = trianglePath,
                        color = if (isOwn) activeSurface else primary
                    )
                }
                .background(
                    color = if (isOwn) activeSurface else primary,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(8.dp)
        ) {
            Row {
                Text(
                    text = message.username,
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.overline
                )
            }
            Text(
                text = message.text,
                style = if (isOwn) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
            )
        }
    }
}

@Preview
@Composable
fun MessageBubblePreview() {
    MessageBubble(message = Message("username", "Example message", "10:10"), false)
}