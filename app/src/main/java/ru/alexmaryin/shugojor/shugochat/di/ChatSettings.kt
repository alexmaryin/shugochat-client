package ru.alexmaryin.shugojor.shugochat.di

import androidx.datastore.preferences.core.stringPreferencesKey

object ChatSettings {
    val TOKEN_KEY = stringPreferencesKey("token")
    val USERNAME_KEY = stringPreferencesKey("username")
}