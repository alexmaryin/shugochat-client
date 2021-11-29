package ru.alexmaryin.shugojor.shugochat.api

import ru.alexmaryin.shugojor.shugochat.BuildConfig

object Endpoints {
    private val BASE_URL = if (BuildConfig.DEBUG) "http://192.168.0.100:8080" else "https://shugojor.herokuapp.com"
    private val CHAT_BASE = if (BuildConfig.DEBUG) "ws://192.168.0.100:8080" else "ws://shugojor.herokuapp.com"

    val LOGIN = "$BASE_URL/login"
    val REGISTER = "$BASE_URL/register"
    val CHAT = "$CHAT_BASE/chat"
}