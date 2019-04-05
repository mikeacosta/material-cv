package net.acosta.mike.resume.di

import android.app.Application
import dagger.Module
import dagger.Provides
import net.acosta.mike.resume.data.room.ResumeDatabase
import javax.inject.Singleton


@Module
class RoomModule(app: Application) {

    private var resumeDatabase: ResumeDatabase = ResumeDatabase.getInstance(app.baseContext)!!

    @Singleton
    @Provides
    fun providesRoomDatabase() = resumeDatabase

    @Singleton
    @Provides
    fun providesContentDao() = resumeDatabase.contentDao()

    @Singleton
    @Provides
    fun providesProfileItemDao() = resumeDatabase.profileDao()

    @Singleton
    @Provides
    fun providesJobDao() = resumeDatabase.jobDao()
}