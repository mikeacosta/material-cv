package net.acosta.mike.resume.data.api

import net.acosta.mike.resume.data.Profile
import retrofit2.Call
import retrofit2.http.GET

interface ProfileService {
    @GET("/Prod/api/profile")
    fun getProfile(): Call<Profile>
}