package ru.alexmaryin.shugojor.shugochat.features.login

data class LoginScreenState(
    val processing: Boolean = false,
    val loginSuccess: Boolean = false,
    val loginFail: Boolean = false,
)
