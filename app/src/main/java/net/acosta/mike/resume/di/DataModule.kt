package net.acosta.mike.resume.di

import dagger.Module
import dagger.Provides
import net.acosta.mike.resume.data.*
import net.acosta.mike.resume.data.api.ApiClient
import net.acosta.mike.resume.data.room.ContentDao
import net.acosta.mike.resume.data.room.JobDao
import net.acosta.mike.resume.data.room.ProfileDao
import net.acosta.mike.resume.utils.AppStatus
import net.acosta.mike.resume.utils.CustomExecutor
import javax.inject.Singleton


@Module
class DataModule {
    @Provides
    @Singleton
    fun providesJobRepository(apiClient: ApiClient,
                              jobDao: JobDao,
                              executor: CustomExecutor,
                              appStatus: AppStatus)
            = JobRepository(apiClient, jobDao, executor, appStatus)

    @Provides
    @Singleton
    fun providesContentRepository(apiClient: ApiClient,
                                  contentDao: ContentDao,
                                  executor: CustomExecutor,
                                  appStatus: AppStatus)
            = ContentRepository(apiClient, contentDao, executor, appStatus)

    @Provides
    @Singleton
    fun providesProfileRepository(apiClient: ApiClient,
                                  profileDao: ProfileDao,
                                  executor: CustomExecutor,
                                  appStatus: AppStatus)
            = ProfileRepository(apiClient, profileDao, executor, appStatus)
}