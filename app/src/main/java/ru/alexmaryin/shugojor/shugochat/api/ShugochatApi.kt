package ru.alexmaryin.shugojor.shugochat.api

import ru.alexmaryin.shugojor.shugochat.api.model.Credentials
import ru.alexmaryin.shugojor.shugochat.api.model.User

interface ShugochatApi {
    /**
    * Try to login on server.
    * @param credentials [Credentials] data class with username and password as strings
    * @return string with issued JWT token if login is success or null if fails
     */
    suspend fun login(credentials: Credentials): String?

    /**
     * Try to register a new user
     * @param user [User] data class with user details (name, email, password, etc)
     * @return True if register is success or False if register is failed
     */
    suspend fun register(user: User): Boolean
}