package ru.alexmaryin.shugojor.shugochat.features.login

import ru.alexmaryin.shugojor.shugochat.data.result.Result

data class LoginScreenState(
    val processing: Boolean = false,
    val loginError: Result.Error? = null
)
