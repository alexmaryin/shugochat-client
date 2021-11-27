package ru.alexmaryin.shugojor.shugochat.api

import android.util.Log
import androidx.fragment.app.FragmentTabHost
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.alexmaryin.shugojor.shugochat.api.model.Credentials
import ru.alexmaryin.shugojor.shugochat.api.model.MessageApi
import ru.alexmaryin.shugojor.shugochat.api.model.User

class ShugochatApiImpl(
    private val client: HttpClient
) : ShugochatApi {

    private var chatSession: WebSocketSession? = null

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

    override suspend fun openChat(token: String): Boolean {
        chatSession = client.webSocketSession {
            url(Endpoints.CHAT)
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        return chatSession?.isActive ?: false
    }

    override suspend fun observeIncoming(): Flow<MessageApi> =
        chatSession?.let { chat ->
            chat.incoming.receiveAsFlow()
                .filter { it is Frame.Text || it is Frame.Close }
                .map {
                    if (it is Frame.Text) {
                        Log.d("CHAT", "New incoming message ${it.readText()}")
                        Json.decodeFromString(it.readText())
                    } else {
                        MessageApi(
                            "SYSTEM",
                            (it as Frame.Close).readReason()!!.message,
                            System.currentTimeMillis()
                        )
                    }
                }
        } ?: emptyFlow()

    override suspend fun send(message: MessageApi) {
        chatSession?.let {
            it.outgoing.send(Frame.Text(Json.encodeToString(message)))
            Log.d("CHAT", "Message sent to server")
        }
    }

    override suspend fun closeChat(username: String) {
        Log.d("CHAT", "Command to close chat invoked")
        chatSession?.close(CloseReason(CloseReason.Codes.NORMAL, username))
    }

    @Serializable
    data class TokenResponse(val token: String)
}