package ru.alexmaryin.shugojor.shugochat.data.messages.repository

import ru.alexmaryin.shugojor.shugochat.data.messages.database.MessagesDao
import ru.alexmaryin.shugojor.shugochat.data.messages.model.MessageDto

class MessagesRepositoryImpl(
    private val database: MessagesDao
) : MessagesRepository {
    override suspend fun getAll(): List<MessageDto> =
        database.getAll()

    override suspend fun insert(message: MessageDto): Boolean =
        try {
            database.insert(message)
            true
        } catch (e: Exception) {
            false
        }

    override suspend fun clear() =
        database.clear()
}