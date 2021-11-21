package ru.alexmaryin.shugojor.shugochat.features.register

import ru.alexmaryin.shugojor.shugochat.api.model.User

sealed class RegisterEvent {
    data class Register(val user: User) : RegisterEvent()
    object BackToLogin :RegisterEvent()
}
