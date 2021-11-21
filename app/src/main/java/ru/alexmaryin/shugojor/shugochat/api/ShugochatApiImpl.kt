package ru.alexmaryin.shugojor.shugochat.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import ru.alexmaryin.shugojor.shugochat.api.model.Credentials
import ru.alexmaryin.shugojor.shugochat.api.model.User

class ShugochatApiImpl(
    private val client: HttpClient
) : ShugochatApi {
    override suspend fun login(credentials: Credentials): String? {
        val response: HttpResponse = client.post(Endpoints.LOGIN) {
            contentType(ContentType.Application.Json)
            body = credentials
        }
        return if (response.status == HttpStatusCode.OK)
            response.receive<TokenResponse>().token
        else null
    }

    override suspend fun register(user: User): Boolean {
        val response: HttpResponse = client.post(Endpoints.REGISTER) {
            contentType(ContentType.Application.Json)
            body = user
        }
        return response.status == HttpStatusCode.OK
    }

    @Serializable
    data class TokenResponse(val token: String)
}