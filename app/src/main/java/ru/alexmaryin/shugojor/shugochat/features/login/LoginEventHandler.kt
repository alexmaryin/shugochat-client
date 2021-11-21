package ru.alexmaryin.shugojor.shugochat.features.login

fun interface LoginEventHandler {
    fun onEvent(event: LoginEvent)
}