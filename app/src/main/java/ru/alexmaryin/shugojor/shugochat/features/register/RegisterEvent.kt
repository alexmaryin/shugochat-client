package ru.alexmaryin.shugojor.shugochat.features.register

import android.content.Context
import ru.alexmaryin.shugojor.shugochat.api.model.User

sealed class RegisterEvent {
    data class Register(val user: User, val context: Context) : RegisterEvent()
    object BackToLogin :RegisterEvent()
}
