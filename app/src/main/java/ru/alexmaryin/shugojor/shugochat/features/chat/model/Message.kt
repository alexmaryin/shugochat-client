package ru.alexmaryin.shugojor.shugochat.features.chat.model

import ru.alexmaryin.shugojor.shugochat.data.messages.model.MessageDto
import java.text.SimpleDateFormat
import java.util.*

data class Message(
    val username: String,
    val text: String,
    val timestamp: String
)

fun MessageDto.toChatMessage() = Message(
    username = username,
    text = text,
    timestamp = SimpleDateFormat("HH:mm", Locale.getDefault()).format(timestamp)
)
