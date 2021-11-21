package ru.alexmaryin.shugojor.shugochat.features.register

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alexmaryin.shugojor.shugochat.R

@Composable
fun Register() {
    val viewModel: RegisterViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val successText = stringResource(R.string.success_register_text)
    val failedText = stringResource(R.string.failed_register_text)

    viewModel.state.value.success?.let { success ->
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = if(success) successText else failedText
            )
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        RegisterScreen(eventHandler = viewModel)
    }
}