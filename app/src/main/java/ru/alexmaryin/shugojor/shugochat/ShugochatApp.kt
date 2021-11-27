package ru.alexmaryin.shugojor.shugochat

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShugochatApp : Application()

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "chat_settings")