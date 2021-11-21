package ru.alexmaryin.shugojor.shugochat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import ru.alexmaryin.shugojor.shugochat.navigation.Navigator
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApi
import ru.alexmaryin.shugojor.shugochat.api.ShugochatApiImpl
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
    }

    @Provides
    @Singleton
    fun provideApi(): ShugochatApi = ShugochatApiImpl(apiClient)

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = Navigator()
}