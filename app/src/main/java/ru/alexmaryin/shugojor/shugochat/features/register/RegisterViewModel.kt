package ru.alexmaryin.shugojor.shugochat.features.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApi
import ru.alexmaryin.shugojor.shugochat.api.model.Credentials
import ru.alexmaryin.shugojor.shugochat.data.result.ErrorType
import ru.alexmaryin.shugojor.shugochat.data.result.Result
import ru.alexmaryin.shugojor.shugochat.data.result.forError
import ru.alexmaryin.shugojor.shugochat.data.result.forSuccess
import ru.alexmaryin.shugojor.shugochat.dataStore
import ru.alexmaryin.shugojor.shugochat.di.ChatSettings
import ru.alexmaryin.shugojor.shugochat.navigation.NavTarget
import ru.alexmaryin.shugojor.shugochat.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val api: ShugochatApi,
    private val navigator: Navigator
) : ViewModel(), RegisterEventHandler {

    private val _state = mutableStateOf(RegisterScreenState())
    val state: State<RegisterScreenState> get() = _state

    override fun onEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.Register -> viewModelScope.launch(Dispatchers.IO) {
                _state.value = RegisterScreenState(processing = true)
                val result = api.register(event.user)
                result.forSuccess { isRegistered ->
                    if (isRegistered) {
                        val login = api.login(Credentials(event.user.name, event.user.password))
                        login.forSuccess { token ->
                            event.context.dataStore.edit { prefs ->
                                prefs[ChatSettings.TOKEN_KEY] = token
                                prefs[ChatSettings.USERNAME_KEY] = event.user.name
                            }
                            navigator.navigateTo(NavTarget.Chat)
                        }
                        login.forError { error ->
                            _state.value = RegisterScreenState(processing = false, error)
                        }
                    } else {
                        _state.value = RegisterScreenState(processing = false, Result.Error(ErrorType.BAD_USERNAME))
                    }
                }
                result.forError { error ->
                    _state.value = RegisterScreenState(processing = false, error)
                }
            }
            RegisterEvent.BackToLogin -> viewModelScope.launch {
                _state.value = RegisterScreenState()
                navigator.navigateTo(NavTarget.Back)
            }
        }
    }
}