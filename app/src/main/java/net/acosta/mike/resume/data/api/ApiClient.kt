package net.acosta.mike.resume.data.api

import net.acosta.mike.resume.data.*
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class ApiClient(baseUrl: String = "https://kynp81ncr8.execute-api.us-west-2.amazonaws.com") {
    private val jobService: JobService
    private val contentService: ContentService
    private val profileService: ProfileService
    private val authenticationService: AuthenticationService

    private val apiCredentials = ApiCredentials()

    var token: String = ""

    init {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    val requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer $token")

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
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

        val authHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val credentials = Credentials.basic(apiCredentials.username, apiCredentials.password)

                    val requestBuilder = original.newBuilder()
                            .header("Authorization", credentials)
                            .header("Accept", "application/json")

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val authRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(authHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        authenticationService = authRetrofit.create(AuthenticationService::class.java)
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

    fun login(): Call<String> {
        return authenticationService.login(apiCredentials)
    }
}