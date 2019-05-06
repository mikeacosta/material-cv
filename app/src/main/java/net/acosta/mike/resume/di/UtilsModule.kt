package net.acosta.mike.resume.di

import android.app.Application
import dagger.Module
import dagger.Provides
import net.acosta.mike.resume.data.api.ApiClient
import net.acosta.mike.resume.data.api.TokenProvider
import net.acosta.mike.resume.utils.AppStatus
import net.acosta.mike.resume.utils.CustomExecutor
import net.acosta.mike.resume.utils.FontCache
import javax.inject.Singleton

@Module
class UtilsModule(private val app: Application) {

    @Provides
    fun providesExecutor() = CustomExecutor()

    @Singleton
    @Provides
    fun providesAppStatus() = AppStatus(app)

    @Singleton
    @Provides
    fun providesFontCache() = FontCache(app)

    @Singleton
    @Provides
    fun providesTokenProvider(apiClient: ApiClient) = TokenProvider(app, apiClient)
}