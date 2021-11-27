package ru.alexmaryin.shugojor.shugochat.features.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApi
import ru.alexmaryin.shugojor.shugochat.dataStore
import ru.alexmaryin.shugojor.shugochat.di.ChatSettings
import ru.alexmaryin.shugojor.shugochat.navigation.NavTarget
import ru.alexmaryin.shugojor.shugochat.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: ShugochatApi,
    private val navigator: Navigator,
) : ViewModel(), LoginEventHandler {

    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> get() = _state

    override fun onEvent(event: LoginEvent) {
        when (event) {

            is LoginEvent.SignIn -> viewModelScope.launch(Dispatchers.IO) {
                _state.value = state.value.copy(processing = true)
                val result = api.login(event.credentials)
                if (result != null) {
                    event.context.dataStore.edit {
                        it[ChatSettings.TOKEN_KEY] = result
                        it[ChatSettings.USERNAME_KEY] = event.credentials.name
                    }
                    navigator.navigateTo(NavTarget.Chat)
                } else {
                    _state.value = state.value.copy(
                        processing = false,
                        loginFail = true
                    )
                }
            }

            LoginEvent.SignUp -> viewModelScope.launch {
                _state.value = LoginScreenState()
                navigator.navigateTo(NavTarget.Register)
            }
        }
    }
}