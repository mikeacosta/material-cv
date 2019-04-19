package net.acosta.mike.resume.data.api

import net.acosta.mike.resume.data.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class ApiClient(baseUrl: String = "https://kynp81ncr8.execute-api.us-west-2.amazonaws.com") {
    private val jobService: JobService
    private val contentService: ContentService
    private val profileService: ProfileService

    init {
        val httpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
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