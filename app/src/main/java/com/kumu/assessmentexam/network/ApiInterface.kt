package com.kumu.assessmentexam.network

import com.kumu.assessmentexam.data.model.MediaResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("search?term=star&amp;country=au&amp;media=movie&amp;all")
    suspend fun getMedias(): Response<MediaResponse>
}