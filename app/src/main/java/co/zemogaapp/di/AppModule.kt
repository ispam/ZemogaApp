package co.zemogaapp.di

import android.app.Application
import android.content.Context
import co.zemogaapp.utils.DefaultDispatcherProvider
import co.zemogaapp.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideApplication(app: Application): Context = app

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}