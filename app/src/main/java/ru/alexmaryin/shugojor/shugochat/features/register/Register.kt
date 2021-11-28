package ru.alexmaryin.shugojor.shugochat.features.register

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alexmaryin.shugojor.shugochat.ui.components.errorLocalizedString

@Composable
fun Register() {
    val viewModel: RegisterViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    viewModel.state.value.registerError?.let { error ->
        LaunchedEffect(key1 = scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar(errorLocalizedString(error, context))
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        RegisterScreen(
            isProcessing = viewModel.state.value.processing,
            eventHandler = viewModel
        )
    }
}