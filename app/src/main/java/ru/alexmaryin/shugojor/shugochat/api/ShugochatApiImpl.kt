package ru.alexmaryin.shugojor.shugochat.api

import android.util.Log
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
import ru.alexmaryin.shugojor.shugochat.data.result.ErrorType
import ru.alexmaryin.shugojor.shugochat.data.result.Result

class ShugochatApiImpl(
    private val client: HttpClient,
    private val networkStatus: NetworkStatus
) : ShugochatApi {

    private var chatSession: DefaultWebSocketSession? = null

    private suspend fun <R> requestFor(callback: suspend () -> R): Result<R> =
        if (networkStatus.isConnected()) {
            try {
                Result.Success(callback())
            } catch (e: ResponseException) {
                when (e.response.status.value) {
                    HttpStatusCode.RequestTimeout.value -> Result.Error(ErrorType.SERVER_UNAVAILABLE)
                    HttpStatusCode.Unauthorized.value -> Result.Error(ErrorType.UNAUTHORIZED)
                    in 402..499 -> Result.Error(ErrorType.OTHER_CLIENT_ERROR)
                    in HttpStatusCode.InternalServerError.value..HttpStatusCode.GatewayTimeout.value -> Result.Error(ErrorType.SERVER_UNAVAILABLE)
                    in 505..599 -> Result.Error(ErrorType.OTHER_SERVER_ERROR)
                    else -> Result.Error(ErrorType.UNKNOWN, e.localizedMessage ?: e.message)
                }
            } catch (e: Exception) {
                Result.Error(ErrorType.UNKNOWN, e.localizedMessage ?: e.message)
            }
        } else {
            Result.Error(ErrorType.NO_CONNECTION)
        }

    override suspend fun login(credentials: Credentials): Result<String> = requestFor {
        val response: HttpResponse = client.post(Endpoints.LOGIN) {
            contentType(ContentType.Application.Json)
            body = credentials
        }
        response.receive<TokenResponse>().token
    }

    override suspend fun register(user: User): Result<Boolean> = requestFor {
        val response: HttpResponse = client.post(Endpoints.REGISTER) {
            contentType(ContentType.Application.Json)
            body = user
        }
        response.status == HttpStatusCode.OK
    }

    override suspend fun openChat(token: String): Result<Boolean> = requestFor {
        chatSession = client.webSocketSession {
            url(Endpoints.CHAT)
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        chatSession?.isActive ?: false
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