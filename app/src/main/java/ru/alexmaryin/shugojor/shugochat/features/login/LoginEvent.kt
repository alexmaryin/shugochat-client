package ru.alexmaryin.shugojor.shugochat.features.login

import ru.alexmaryin.shugojor.shugochat.api.model.Credentials

sealed class LoginEvent {
    data class SignIn(val credentials: Credentials) : LoginEvent()
    object SignUp : LoginEvent()
}
