package ru.alexmaryin.shugojor.shugochat.features.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.alexmaryin.shugojor.shugochat.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import ru.alexmaryin.shugojor.shugochat.ui.components.CaptionUnderlined
import ru.alexmaryin.shugojor.shugochat.ui.components.MenuItem
import ru.alexmaryin.shugojor.shugochat.ui.components.UserMenu
import ru.alexmaryin.shugojor.shugochat.ui.components.errorLocalizedString
import ru.alexmaryin.shugojor.shugochat.ui.theme.darkBackground

@Composable
fun Chat() {

    val viewModel: ChatViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> viewModel.onEvent(ChatEvent.OpenChat(context))
                Lifecycle.Event.ON_STOP -> viewModel.onEvent(ChatEvent.CloseChat)
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    viewModel.state.value.chatError?.let {
        LaunchedEffect(key1 = scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar(errorLocalizedString(it, context))
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { CaptionUnderlined(text = stringResource(R.string.chat_caption)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(ChatEvent.Back) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = Modifier.size(30.dp),
                            contentDescription = "close chat"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(ChatEvent.SettingsEnter) }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            modifier = Modifier.size(30.dp),
                            contentDescription = "settings"
                        )
                    }
                },
                backgroundColor = darkBackground
            )
        }
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            viewModel.state.value.user?.let {
                ChatScreen(
                    user = it,
                    messages = viewModel.state.value.messages,
                    eventHandler = viewModel
                )
            }

            if (viewModel.state.value.menuVisible) {
                UserMenu(
                    items = listOf(
                        MenuItem(stringResource(R.string.clear_chat)) {
                            viewModel.onEvent(ChatEvent.ClearChat)
                        }
                    ),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    viewModel.onEvent(ChatEvent.SettingsClose)
                }
            }
        }

    }
}
