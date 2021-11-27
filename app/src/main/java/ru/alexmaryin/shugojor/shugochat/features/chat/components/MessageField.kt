package ru.alexmaryin.shugojor.shugochat.features.chat.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import ru.alexmaryin.shugojor.shugochat.ui.theme.activeSurface
import ru.alexmaryin.shugojor.shugochat.ui.theme.surface

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageField(send: (String) -> Unit) {

    var text by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .onFocusEvent { isActive = it.isFocused },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isActive) activeSurface else surface,
            focusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = "Enter something",
                modifier = Modifier.alpha(0.7f),
                style = MaterialTheme.typography.body1,
            )
        },
        maxLines = 10,
        textStyle = MaterialTheme.typography.body1,
        trailingIcon = {
            if (text.isNotBlank()) {
                IconButton(onClick = {
                    keyboard?.hide()
                    send(text)
                    text = ""
                })
                {
                    Icon(
                        imageVector = Icons.Default.Send,
                        modifier = Modifier.size(40.dp),
                        contentDescription = "send"
                    )
                }
            }
        }
    )
}