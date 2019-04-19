package net.acosta.mike.resume.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.acosta.mike.resume.data.*


@Database(entities = arrayOf(Content::class, ProfileItem::class, Job::class), version = 6)
@TypeConverters(Converters::class)
abstract class ResumeDatabase : RoomDatabase() {

    abstract fun contentDao(): ContentDao

    abstract fun profileDao(): ProfileDao

    abstract fun jobDao(): JobDao

    companion object {
        private var INSTANCE: ResumeDatabase? = null

        fun getInstance(context: Context): ResumeDatabase? {
            if (INSTANCE == null) {
                synchronized(ResumeDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ResumeDatabase::class.java, "resume.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}