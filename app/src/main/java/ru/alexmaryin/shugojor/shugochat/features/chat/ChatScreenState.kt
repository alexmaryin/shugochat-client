package ru.alexmaryin.shugojor.shugochat.features.chat

import ru.alexmaryin.shugojor.shugochat.features.chat.model.Message

data class ChatScreenState(
    val user: String? = null,
    val messages: List<Message> = emptyList()
)
