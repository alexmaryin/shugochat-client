package ru.alexmaryin.shugojor.shugochat.features.login

import android.content.Context
import ru.alexmaryin.shugojor.shugochat.api.model.Credentials

sealed class LoginEvent {
    data class SignIn(val credentials: Credentials, val context: Context) : LoginEvent()
    object SignUp : LoginEvent()
}
