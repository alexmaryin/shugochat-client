package ru.alexmaryin.shugojor.shugochat.features.login

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alexmaryin.shugojor.shugochat.R

@Composable
fun Login() {

    val viewModel: LoginViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val wrongText = stringResource(R.string.wrong_login_text)

    if (viewModel.state.value.loginFail == true) LaunchedEffect(scaffoldState.snackbarHostState) {
         scaffoldState.snackbarHostState.showSnackbar(wrongText)
        }

    Scaffold(scaffoldState = scaffoldState) {
        LoginScreen(
            isNotProcessing = viewModel.state.value.processing.not(),
            eventHandler = viewModel
        )
    }
}