package ru.alexmaryin.shugojor.shugochat.data.messages.repository

import ru.alexmaryin.shugojor.shugochat.data.messages.model.MessageDto

interface MessagesRepository {
    suspend fun getAll() : List<MessageDto>
    suspend fun insert(message: MessageDto) : Boolean
    suspend fun clear()
}