package net.acosta.mike.resume.data.api

import net.acosta.mike.resume.data.ApiCredentials
import retrofit2.Call
import retrofit2.http.*

interface AuthenticationService {
    @Headers("Content-Type: application/json")
    @POST("/Prod/api/signin")
    fun login(@Body creds: ApiCredentials): Call<String>
}