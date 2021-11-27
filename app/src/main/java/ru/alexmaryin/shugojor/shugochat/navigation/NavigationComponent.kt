package ru.alexmaryin.shugojor.shugochat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexmaryin.shugojor.shugochat.features.chat.Chat
import ru.alexmaryin.shugojor.shugochat.features.login.Login
import ru.alexmaryin.shugojor.shugochat.features.register.Register

@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator
) {
    LaunchedEffect("navigation") {
        navigator.target.onEach {
            if (it == NavTarget.Back) {
                navController.popBackStack()
            } else {
                navController.navigate(it.route)
            }
        }.launchIn(this)
    }

    NavHost(navController = navController, startDestination = NavTarget.Login.route) {
        composable(NavTarget.Login.route) { Login() }
        composable(NavTarget.Register.route) { Register() }
        composable(NavTarget.Chat.route) { Chat() }
    }
}