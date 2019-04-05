package net.acosta.mike.resume.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import net.acosta.mike.resume.data.Content
import net.acosta.mike.resume.data.Job
import net.acosta.mike.resume.data.ProfileItem

@Dao
interface ContentDao {

    @Query("SELECT * FROM content")
    fun getAll(): List<Content>

    @Insert(onConflict = REPLACE)
    fun insert(content: Content)

    @Query("DELETE FROM content")
    fun deleteAll()
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getAll(): List<ProfileItem>

    @Insert(onConflict = REPLACE)
    fun insert(profileItem: ProfileItem)

    @Query("DELETE FROM profile")
    fun deleteAll()
}

@Dao
interface JobDao {
    @Query("SELECT * FROM job")
    fun getAll(): List<Job>

    @Query("SELECT * FROM job WHERE id = :jobId")
    fun getbyId(jobId: Int): Job

    @Insert(onConflict = REPLACE)
    fun insert(job: Job)

    @Query("DELETE FROM job")
    fun deleteAll()
}