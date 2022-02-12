package com.kumu.assessmentexam.main

import com.kumu.assessmentexam.data.model.MediaResponse
import com.kumu.assessmentexam.network.ApiInterface
import retrofit2.Response

/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */
class MainRepository(private val apiInterface: ApiInterface) {

    suspend fun getMedias(): Response<MediaResponse> {
        return apiInterface.getMovies()
    }
}