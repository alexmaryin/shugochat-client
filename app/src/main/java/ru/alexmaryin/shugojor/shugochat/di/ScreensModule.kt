package ru.alexmaryin.shugojor.shugochat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.alexmaryin.shugojor.shugochat.features.login.LoginEventHandler
import ru.alexmaryin.shugojor.shugochat.features.login.LoginViewModel
import ru.alexmaryin.shugojor.shugochat.features.register.RegisterEventHandler

@Module
@InstallIn(ViewModelComponent::class)
abstract class ScreensModule {

    @ViewModelScoped
    @Binds
    abstract fun bindLoginEventHandler(viewModel: LoginViewModel) : LoginEventHandler

//    @ViewModelScoped
//    @Binds
//    abstract fun bindRegisterEventHandler(viewModel: RegisterViewModel) : RegisterEventHandler
}