package net.acosta.mike.resume.data.api

import net.acosta.mike.resume.data.Jobs
import retrofit2.Call
import retrofit2.http.GET

interface JobService {
    @GET("/Prod/api/jobs")
    fun getJobList(): Call<Jobs>
}