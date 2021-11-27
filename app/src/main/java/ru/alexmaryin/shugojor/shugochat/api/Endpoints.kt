package ru.alexmaryin.shugojor.shugochat.api

object Endpoints {
    private const val BASE_URL = "http://192.168.0.100:8080"
    private const val CHAT_BASE = "ws://192.168.0.100:8080"
    const val LOGIN = "$BASE_URL/login"
    const val REGISTER = "$BASE_URL/register"
    const val CHAT = "$CHAT_BASE/chat"
}