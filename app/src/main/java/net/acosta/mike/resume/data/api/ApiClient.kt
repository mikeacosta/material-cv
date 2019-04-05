package net.acosta.mike.resume.data.api

import net.acosta.mike.resume.data.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient(baseUrl: String = "http://demo3886988.mockable.io") {
    private val jobService: JobService
    private val contentService: ContentService
    private val profileService: ProfileService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        jobService = retrofit.create(JobService::class.java)
        contentService = retrofit.create(ContentService::class.java)
        profileService = retrofit.create(ProfileService::class.java)
    }

    fun getJobs(): Call<Jobs> {
        return jobService.getJobList()
    }

    fun getContent(): Call<Content> {
        return contentService.getContent()
    }

    fun getProfile(): Call<Profile> {
        return profileService.getProfile()
    }
}