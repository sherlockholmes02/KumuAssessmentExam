package com.kumu.assessmentexam.main

import androidx.lifecycle.LiveData
import com.kumu.assessmentexam.data.MediaDatabase
import com.kumu.assessmentexam.data.model.Media
import com.kumu.assessmentexam.data.model.MediaResponse
import com.kumu.assessmentexam.network.ApiInterface
import com.kumu.assessmentexam.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */
class MainRepository(private val apiInterface: ApiInterface, private val mediaDatabase: MediaDatabase) {

    suspend fun getMedias(): Response<MediaResponse> {
        return apiInterface.getMedias()
    }

    suspend fun getMediasFromDbAsList(): List<Media> {
        return withContext(Dispatchers.IO) {
            mediaDatabase.mediaDao().get()
        }
    }

    suspend fun getMediasFromDb(): LiveData<List<Media>> {
        return withContext(Dispatchers.IO) {
            mediaDatabase.mediaDao().getMedias()
        }
    }

    suspend fun getMediasCount(): Int {
        return withContext(Dispatchers.IO) {
            mediaDatabase.mediaDao().getCount()
        }
    }

    fun saveMedias(medias: List<Media>) {
        Coroutines.inputOutput {
            mediaDatabase.mediaDao().insertAllMedias(medias)
        }
    }

    fun updateMedias(medias: List<Media>) {
        Coroutines.inputOutput {
            mediaDatabase.mediaDao().updateMedias(medias)
        }
    }

    fun updateLastVisit(media: Media) {
        Coroutines.inputOutput {
            mediaDatabase.mediaDao().update(media)
        }
    }
}