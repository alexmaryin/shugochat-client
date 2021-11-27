package ru.alexmaryin.shugojor.shugochat.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.alexmaryin.shugojor.shugochat.features.chat.components.MessageBubble
import ru.alexmaryin.shugojor.shugochat.features.chat.components.MessageField
import ru.alexmaryin.shugojor.shugochat.features.chat.components.SystemMessage
import ru.alexmaryin.shugojor.shugochat.features.chat.model.Message
import ru.alexmaryin.shugojor.shugochat.ui.theme.ShugochatTheme
import ru.alexmaryin.shugojor.shugochat.ui.theme.darkBackground

@Composable
fun ChatScreen(
    user: String,
    messages: List<Message>,
    eventHandler: ChatEventHandler
) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = darkBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            items(messages) { message ->
                if (message.username == "SYSTEM") {
                    SystemMessage(message.text)
                } else {
                    MessageBubble(message, message.username == user)
                }
                Spacer(modifier = Modifier.height(28.dp))
            }
        }

        MessageField { eventHandler.onEvent(ChatEvent.Send(it)) }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    val fakeMessages = listOf(
        Message("alex", "Hello", "10:10"),
        Message("java73", "Hello, alex, how's going?", "10:11"),
        Message("alex", "Fine, and you?", "10:15"),
        Message("java73", "Fine, thnks.\nBy the way, this is a second line!", "10:16"),
        Message("java73", "So, good bye.", "11:20"),
        Message("alex", "Bye.", "11:22"),
    )
    ShugochatTheme {
        ChatScreen("java73", fakeMessages) {}
    }
}