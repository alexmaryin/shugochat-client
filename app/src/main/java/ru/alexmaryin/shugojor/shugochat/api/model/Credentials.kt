package ru.alexmaryin.shugojor.shugochat.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val username: String,
    val password: String
)
