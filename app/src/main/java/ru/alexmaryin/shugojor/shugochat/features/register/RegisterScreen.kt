package ru.alexmaryin.shugojor.shugochat.features.register

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
import ru.alexmaryin.shugojor.shugochat.api.model.User
import ru.alexmaryin.shugojor.shugochat.ui.components.CaptionUnderlined
import ru.alexmaryin.shugojor.shugochat.ui.components.MainText
import ru.alexmaryin.shugojor.shugochat.ui.components.PowerButton
import ru.alexmaryin.shugojor.shugochat.ui.components.ShugoTextField
import ru.alexmaryin.shugojor.shugochat.ui.theme.ShugochatTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    isProcessing: Boolean,
    eventHandler: RegisterEventHandler
) {
    var nameInput by rememberSaveable { mutableStateOf("") }
    var passwordInput by rememberSaveable { mutableStateOf("") }
    var emailInput by rememberSaveable { mutableStateOf("") }
    var birthdateInput by rememberSaveable { mutableStateOf("") }
    val canRegister by remember {
        mutableStateOf({
            nameInput.isNotEmpty() && passwordInput.isNotEmpty() && nameInput != "SYSTEM" && isProcessing.not()
        })
    }

    val scrollState = rememberScrollState()
    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CaptionUnderlined(text = stringResource(R.string.register_caption))

            ShugoTextField(
                value = nameInput,
                onChange = { nameInput = it },
                hint = stringResource(R.string.username_hint),
                isMandatory = true
            )

            ShugoTextField(
                value = emailInput,
                onChange = { emailInput = it },
                hint = stringResource(R.string.email_hint),
            )

            ShugoTextField(
                value = birthdateInput,
                onChange = { birthdateInput = it },
                hint = stringResource(R.string.birthdate_hint),
            )

            ShugoTextField(
                value = passwordInput,
                onChange = { passwordInput = it },
                hint = stringResource(R.string.password_hint),
                isPassword = true,
                isMandatory = true
            )

            PowerButton(
                isEnabled = canRegister(),
                isAnimated = isProcessing,
                contentDescription = stringResource(R.string.register_caption)
            ) {
                keyboard?.hide()
                eventHandler.onEvent(
                    RegisterEvent.Register(
                        User(
                            name = nameInput,
                            email = emailInput,
                            password = passwordInput,
                            birthdate = parseBirthdate(birthdateInput),
                        ),
                        context
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            MainText(stringResource(R.string.back_to_login))
            MainText(stringResource(R.string.sigin_text), true) {
                eventHandler.onEvent(RegisterEvent.BackToLogin)
            }
        }
    }
}

@Preview
@Composable
fun RegisterPreview(navController: NavController = rememberNavController()) {
    ShugochatTheme {
        RegisterScreen(true) {
            when (it) {
                is RegisterEvent.Register -> Log.d("LOGIN", "Register new user: ${it.user}")
                RegisterEvent.BackToLogin -> navController.popBackStack()
            }
        }
    }
}

fun parseBirthdate(input: String): Long? = try {
    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(input)?.time
} catch (e: Exception) {
    null
}
