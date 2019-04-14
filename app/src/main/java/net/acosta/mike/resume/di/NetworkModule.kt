package net.acosta.mike.resume.di

import dagger.Module
import dagger.Provides
import net.acosta.mike.resume.data.api.ApiClient
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://kynp81ncr8.execute-api.us-west-2.amazonaws.com"
    }

    @Provides
    @Singleton
    fun providesApiClient() = ApiClient(BASE_URL)
}