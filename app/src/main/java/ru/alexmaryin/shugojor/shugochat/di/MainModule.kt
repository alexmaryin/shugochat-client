package ru.alexmaryin.shugojor.shugochat.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import ru.alexmaryin.shugojor.shugochat.navigation.Navigator
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApi
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApiImpl
import ru.alexmaryin.shugojor.shugochat.data.messages.database.MessagesDb
import ru.alexmaryin.shugojor.shugochat.data.messages.repository.MessagesRepository
import ru.alexmaryin.shugojor.shugochat.data.messages.repository.MessagesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    private val apiClient = HttpClient(CIO) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(WebSockets)
    }

    @Provides
    @Singleton
    fun provideApi(): ShugochatApi = ShugochatApiImpl(apiClient)

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = Navigator()

    @Provides
    @Singleton
    fun provideMessagesDb(@ApplicationContext context: Context): MessagesDb =
        Room.databaseBuilder(
            context,
            MessagesDb::class.java,
            MessagesDb.DB_NAME
        ).build()

    @Provides
    @Singleton
    fun provideMessagesRepository(database: MessagesDb): MessagesRepository =
        MessagesRepositoryImpl(database.dao())
}
