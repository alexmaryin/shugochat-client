package ru.alexmaryin.shugojor.shugochat.features.login

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alexmaryin.shugojor.shugochat.ui.components.errorLocalizedString

@Composable
fun Login() {

    val viewModel: LoginViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    viewModel.state.value.loginError?.let { error ->
        LaunchedEffect(key1 = scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar(errorLocalizedString(error, context))
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        LoginScreen(
            isProcessing = viewModel.state.value.processing,
            eventHandler = viewModel
        )
    }
}