package ru.alexmaryin.shugojor.shugochat.api.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val email: String,
    val password: String,
    val birthdate: Long? = null,
    val role: UserRole = UserRole.REGULAR
)

enum class UserRole {
    ADMIN, REGULAR
}