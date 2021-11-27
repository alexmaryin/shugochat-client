package ru.alexmaryin.shugojor.shugochat.data.messages.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.alexmaryin.shugojor.shugochat.data.messages.model.MessageDto

@Database(entities = [MessageDto::class], version = 1)
abstract class MessagesDb : RoomDatabase() {
    abstract fun dao(): MessagesDao

    companion object {
        const val DB_NAME = "chat_db"
    }
}