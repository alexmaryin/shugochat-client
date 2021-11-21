package ru.alexmaryin.shugojor.shugochat.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
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
        return try {
            val response: HttpResponse = client.post(Endpoints.LOGIN) {
                contentType(ContentType.Application.Json)
                body = credentials
            }
            response.receive<TokenResponse>().token
        } catch (e: ClientRequestException) {
            null
        } catch (e: ServerResponseException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun register(user: User): Boolean {
        return try {
            val response: HttpResponse = client.post(Endpoints.REGISTER) {
                contentType(ContentType.Application.Json)
                body = user
            }
            return response.status == HttpStatusCode.OK
        } catch (e: ClientRequestException) {
            false
        } catch (e: ServerResponseException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    @Serializable
    data class TokenResponse(val token: String)
}