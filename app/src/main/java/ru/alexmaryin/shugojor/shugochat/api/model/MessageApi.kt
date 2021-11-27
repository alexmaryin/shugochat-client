package ru.alexmaryin.shugojor.shugochat.api.model

import kotlinx.serialization.Serializable
import ru.alexmaryin.shugojor.shugochat.data.messages.model.MessageDto

@Serializable
data class MessageApi(
    val username: String,
    val text: String,
    val timestamp: Long
)

fun MessageApi.toDto() = MessageDto(
    username = username,
    text = text,
    timestamp = timestamp,
)

