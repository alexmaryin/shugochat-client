package ru.alexmaryin.shugojor.shugochat.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

    private val _target = MutableSharedFlow<NavTarget>(extraBufferCapacity = 1)
    val target get() = _target.asSharedFlow()

    suspend fun navigateTo(target: NavTarget) {
        _target.emit(target)
    }
}

enum class NavTarget(val route: String) {
    Login("login"),
    Register("register"),
    Back(""),
    Chat("chat")
}