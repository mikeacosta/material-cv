package net.acosta.mike.resume.data.api

import net.acosta.mike.resume.data.Content
import retrofit2.Call
import retrofit2.http.GET


interface ContentService {
    @GET("/api/resume/content")
    fun getContent(): Call<Content>
}