package ru.alexmaryin.shugojor.shugochat.api

import io.ktor.client.*
import ru.alexmaryin.shugojor.shugochat.api.model.Credentials
import ru.alexmaryin.shugojor.shugochat.api.model.User
import javax.inject.Inject

class ShugochatApiImpl(
    private val client: HttpClient
) : ShugochatApi {
    override fun login(body: Credentials): String? {
        TODO("Not yet implemented")
    }

    override fun register(user: User): Boolean {
        TODO("Not yet implemented")
    }
}