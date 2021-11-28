package ru.alexmaryin.shugojor.shugochat.features.chat

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApi
import ru.alexmaryin.shugojor.shugochat.api.model.MessageApi
import ru.alexmaryin.shugojor.shugochat.api.model.toDto
import ru.alexmaryin.shugojor.shugochat.data.messages.repository.MessagesRepository
import ru.alexmaryin.shugojor.shugochat.data.result.forError
import ru.alexmaryin.shugojor.shugochat.data.result.forSuccess
import ru.alexmaryin.shugojor.shugochat.dataStore
import ru.alexmaryin.shugojor.shugochat.di.ChatSettings
import ru.alexmaryin.shugojor.shugochat.features.chat.model.toChatMessage
import ru.alexmaryin.shugojor.shugochat.navigation.NavTarget
import ru.alexmaryin.shugojor.shugochat.navigation.Navigator
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val api: ShugochatApi,
    private val navigator: Navigator,
    private val messagesRepository: MessagesRepository
) : ViewModel(), ChatEventHandler {

    private val _state = mutableStateOf(ChatScreenState())
    val state: State<ChatScreenState> get() = _state

    private var username: String? = null
    private var token: String? = null

    private fun retrieveMessages() = viewModelScope.launch(Dispatchers.IO) {
        _state.value = state.value.copy(
            user = username,
            messages = messagesRepository.getAll().map { it.toChatMessage() }
        )
    }

    private fun openChat(context: Context) = viewModelScope.launch {
        val settings = context.dataStore.data.first()
        username = settings[ChatSettings.USERNAME_KEY]
        token = settings[ChatSettings.TOKEN_KEY]
        retrieveMessages()

        val result = api.openChat(requireNotNull(token))
        result.forSuccess { isActive ->
            if (isActive) {
                api.observeIncoming()
                    .onEach {
                        val dtoMessage = it.toDto()
                        if (dtoMessage.username != "SYSTEM")
                            messagesRepository.insert(dtoMessage)
                        _state.value = state.value.copy(messages = buildList {
                            add(dtoMessage.toChatMessage())
                            addAll(state.value.messages)
                        })
                    }.launchIn(viewModelScope)
            }
        }
        result.forError { error ->
            _state.value = state.value.copy(chatError = error)
        }
    }

    override fun onEvent(event: ChatEvent) {
        viewModelScope.launch {
            when (event) {
                is ChatEvent.OpenChat -> openChat(event.context)

                ChatEvent.CloseChat -> api.closeChat(requireNotNull(username))

                ChatEvent.Back -> {
                    api.closeChat(requireNotNull(username))
                    navigator.navigateTo(NavTarget.Back)
                }

                ChatEvent.ClearChat -> {
                    messagesRepository.clear()
                    _state.value = ChatScreenState(user = username)
                }

                is ChatEvent.Send -> {
                    val message = MessageApi(
                        requireNotNull(username),
                        event.message,
                        System.currentTimeMillis()
                    )
                    api.send(message)
                }

                ChatEvent.SettingsEnter -> _state.value = state.value.copy(menuVisible = true)

                ChatEvent.SettingsClose -> _state.value = state.value.copy(menuVisible = false)
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch { username?.let { api.closeChat(it) } }
        super.onCleared()
    }
}
