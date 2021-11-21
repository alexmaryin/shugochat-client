package ru.alexmaryin.shugojor.shugochat.features.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApi
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: ShugochatApi,
) : ViewModel(), LoginEventHandler {

    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> get() = _state

    override fun onEvent(event: LoginEvent) {
        when(event) {

            is LoginEvent.SignIn -> viewModelScope.launch(Dispatchers.IO) {
                _state.value = state.value.copy(processing = true)
                val result = api.login(event.credentials)
                _state.value = state.value.copy(
                    processing = false,
                    loginSuccess = result != null,
                    loginFail = result == null
                )
            }

            LoginEvent.SignUp -> {
                _state.value = state.value.copy(register = true)
                Log.d("LOGIN", "Invoke register screen")
            }
        }
    }
}