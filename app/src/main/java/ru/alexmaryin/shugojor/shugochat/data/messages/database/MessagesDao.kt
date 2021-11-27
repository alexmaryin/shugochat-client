package ru.alexmaryin.shugojor.shugochat.data.messages.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.alexmaryin.shugojor.shugochat.data.messages.model.MessageDto

@Dao
interface MessagesDao {
    @Query("select * from messages order by timestamp desc")
    suspend fun getAll(): List<MessageDto>

    @Insert
    suspend fun insert(message: MessageDto)

    @Query("delete from messages")
    suspend fun clear()
}