package ru.alexmaryin.shugojor.shugochat.features.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApi
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
                _state.value = state.value.copy(processing = true, success = null)
                val result = api.register(event.user)
                _state.value = state.value.copy(processing = false, success = result)
            }
            RegisterEvent.BackToLogin -> viewModelScope.launch {
                _state.value = RegisterScreenState()
                navigator.navigateTo(NavTarget.Back)
            }
        }
    }
}