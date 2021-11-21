package ru.alexmaryin.shugojor.shugochat.features.register

fun interface RegisterEventHandler {
    fun onEvent(event: RegisterEvent)
}