package net.acosta.mike.resume.di

import dagger.Module
import dagger.Provides
import net.acosta.mike.resume.data.*
import net.acosta.mike.resume.viewmodel.*

@Module
object ViewModelModule {
    @JvmStatic
    @Provides
    fun jobsViewModel(repo: JobRepository) = JobsViewModel(repo)

    @JvmStatic
    @Provides
    fun jobViewModel(repo: JobRepository) = JobViewModel(repo)

    @JvmStatic
    @Provides
    fun contentViewModel(repo: ContentRepository) = ContentViewModel(repo)

    @JvmStatic
    @Provides
    fun profileViewModel(repo: ProfileRepository) = ProfileViewModel(repo)
}