package ru.alexmaryin.shugojor.shugochat.features.chat

import android.content.Context

sealed class ChatEvent {
    data class OpenChat(val context: Context) : ChatEvent()
    object ClearChat : ChatEvent()
    object CloseChat : ChatEvent()
    object Back : ChatEvent()
    object SettingsEnter : ChatEvent()
    data class Send(val message: String) : ChatEvent()
}
