package ru.alexmaryin.shugojor.shugochat.data.messages.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageDto(
    val username: String,
    val text: String,
    val timestamp: Long,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
