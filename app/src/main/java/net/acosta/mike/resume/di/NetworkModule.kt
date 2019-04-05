package net.acosta.mike.resume.di

import dagger.Module
import dagger.Provides
import net.acosta.mike.resume.data.api.ApiClient
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "http://demo3886988.mockable.io"
    }

    @Provides
    @Singleton
    fun providesApiClient() = ApiClient(BASE_URL)
}