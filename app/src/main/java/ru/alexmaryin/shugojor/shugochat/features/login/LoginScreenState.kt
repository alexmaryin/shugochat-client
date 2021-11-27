package ru.alexmaryin.shugojor.shugochat.features.login

data class LoginScreenState(
    val processing: Boolean = false,
    val loginFail: Boolean? = null
)
