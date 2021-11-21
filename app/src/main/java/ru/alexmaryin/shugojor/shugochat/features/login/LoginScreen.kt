package ru.alexmaryin.shugojor.shugochat.features.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.alexmaryin.shugojor.shugochat.R
import ru.alexmaryin.shugojor.shugochat.api.model.Credentials
import ru.alexmaryin.shugojor.shugochat.ui.components.CaptionUnderlined
import ru.alexmaryin.shugojor.shugochat.ui.components.MainText
import ru.alexmaryin.shugojor.shugochat.ui.components.ShugoTextField
import ru.alexmaryin.shugojor.shugochat.ui.theme.ShugochatTheme
import ru.alexmaryin.shugojor.shugochat.ui.theme.onPrimary
import ru.alexmaryin.shugojor.shugochat.ui.theme.primary
import ru.alexmaryin.shugojor.shugochat.ui.theme.textSecondary

@Composable
fun LoginScreen(
    name: String,
    password: String,
    eventHandler: LoginEventHandler
) {

    var nameInput by rememberSaveable { mutableStateOf(name) }
    var passwordInput by rememberSaveable { mutableStateOf(password) }
    val canLogin by remember { mutableStateOf({nameInput.isNotEmpty() && passwordInput.isNotEmpty()}) }
    val scrollState = rememberScrollState()

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

            IconButton(
                onClick = {
                    eventHandler.onEvent(
                        LoginEvent.SignIn(
                            Credentials(
                                nameInput,
                                passwordInput
                            )
                        )
                    )
                },
                modifier = Modifier
                    .padding(20.dp)
                    .size(100.dp)
                    .shadow(10.dp, RoundedCornerShape(50), true)
                    .background(color = primary),
                enabled = canLogin()
            ) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    contentDescription = stringResource(R.string.login_caption),
                    modifier = Modifier.fillMaxSize(),
                    tint = if(canLogin()) onPrimary else textSecondary
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
        LoginScreen(name = "", password = "") {
            when (it) {
                is LoginEvent.SignIn -> Log.d(
                    "LOGIN",
                    "Login ${it.credentials.username} with password ${it.credentials.password}"
                )
                LoginEvent.SignUp -> navController.navigate("register")
            }

        }
    }
}

