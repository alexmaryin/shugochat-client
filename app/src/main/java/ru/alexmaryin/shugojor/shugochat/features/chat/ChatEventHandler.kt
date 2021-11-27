package ru.alexmaryin.shugojor.shugochat.features.chat

fun interface ChatEventHandler {
    fun onEvent(event: ChatEvent)
}