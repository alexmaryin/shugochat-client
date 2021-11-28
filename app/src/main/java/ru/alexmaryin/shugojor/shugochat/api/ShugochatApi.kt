package ru.alexmaryin.shugojor.shugochat.api

import kotlinx.coroutines.flow.Flow
import ru.alexmaryin.shugojor.shugochat.api.model.Credentials
import ru.alexmaryin.shugojor.shugochat.api.model.MessageApi
import ru.alexmaryin.shugojor.shugochat.api.model.User
import ru.alexmaryin.shugojor.shugochat.data.result.Result

interface ShugochatApi {
    /**
    * Try to login on server.
    * @param credentials [Credentials] data class with username and password as strings
    * @return [Result.Success] with [String] with issued JWT token if login is success or [Result.Error] if fails
     */
    suspend fun login(credentials: Credentials): Result<String>

    /**
     * Try to register a new user
     * @param user [User] data class with user details (name, email, password, etc)
     * @return [Result.Success] with True if register is success or False if register is failed
     */
    suspend fun register(user: User): Result<Boolean>

    /**
     * Try to open webSocket session for chat
     * @param token [String] JWT token for authorization
     * @return [Result.Success] with True if chat session opened successfully
     */
    suspend fun openChat(token: String): Result<Boolean>

    /**
     * Sends a new message to the chat
     * @param message [MessageApi] data class with username and text of the message
     */
    suspend fun send(message: MessageApi)

    /**
     * Observes incoming messages from active socket session
     * @return [Flow] of [MessageApi]
     */
    suspend fun observeIncoming(): Flow<MessageApi>

    /**
     * Sends Close frame to the opened webSocket session
     * @param username [String] name of user who is leaving the chat
     */
    suspend fun closeChat(username: String)
}