package ru.alexmaryin.shugojor.shugochat.features.register

import ru.alexmaryin.shugojor.shugochat.data.result.Result

data class RegisterScreenState(
    val processing: Boolean = false,
    val registerError: Result.Error? = null
)
