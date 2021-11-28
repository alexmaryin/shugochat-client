package ru.alexmaryin.shugojor.shugochat.features.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.alexmaryin.shugojor.shugochat.R
import ru.alexmaryin.shugojor.shugochat.api.model.Credentials
import ru.alexmaryin.shugojor.shugochat.ui.components.CaptionUnderlined
import ru.alexmaryin.shugojor.shugochat.ui.components.MainText
import ru.alexmaryin.shugojor.shugochat.ui.components.PowerButton
import ru.alexmaryin.shugojor.shugochat.ui.components.ShugoTextField
import ru.alexmaryin.shugojor.shugochat.ui.theme.ShugochatTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    isProcessing: Boolean,
    eventHandler: LoginEventHandler
) {

    var nameInput by rememberSaveable { mutableStateOf("") }
    var passwordInput by rememberSaveable { mutableStateOf("") }
    val canLogin by remember { mutableStateOf({
        nameInput.isNotEmpty() && passwordInput.isNotEmpty() && isProcessing.not()
    }) }

    val scrollState = rememberScrollState()
    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(state = scrollState),
    ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CaptionUnderlined(text = stringResource(R.string.login_caption))

            ShugoTextField(
                value = nameInput,
                onChange = { nameInput = it },
                hint = stringResource(R.string.username_hint),
                isPassword = false
            )

            ShugoTextField(
                value = passwordInput,
                onChange = { passwordInput = it },
                hint = stringResource(R.string.password_hint),
                isPassword = true
            )

            PowerButton(
                isEnabled = canLogin(),
                isAnimated = isProcessing,
                contentDescription = stringResource(R.string.login_caption)) {
                keyboard?.hide()
                eventHandler.onEvent(
                    LoginEvent.SignIn(Credentials(nameInput, passwordInput), context)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            MainText(stringResource(R.string.not_register_text))
            MainText(stringResource(R.string.signup_text), true) { eventHandler.onEvent(LoginEvent.SignUp) }
        }
    }
}

@Preview
@Composable
fun LoginPreview(navController: NavController = rememberNavController()) {
    ShugochatTheme {
        LoginScreen(false) {
            when (it) {
                is LoginEvent.SignIn -> Log.d(
                    "LOGIN",
                    "Login ${it.credentials.name} with password ${it.credentials.password}"
                )
                LoginEvent.SignUp -> navController.navigate("register")
            }

        }
    }
}

