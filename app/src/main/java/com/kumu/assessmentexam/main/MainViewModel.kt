package com.kumu.assessmentexam.main

import androidx.lifecycle.ViewModel
import com.kumu.assessmentexam.data.model.Media
import com.kumu.assessmentexam.utils.Coroutines
import com.kumu.assessmentexam.utils.lazyDeferred

/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */
class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    var mainListener: MainInterface? = null

    val medias by lazyDeferred {
        mainRepository.getMediasFromDb()
    }

    fun getMedias() {
        mainListener?.showProgressBar()
        Coroutines.inputOutput {
            val movieResponse = mainRepository.getMedias()
            if (movieResponse.code() == 200) {
                mainListener?.onSuccessfulFetch(movieResponse.body()?.media!!)
                //Save to local db after successful fetching from api
                if (mainRepository.getMediasCount() == 0) {
                    mainRepository.saveMedias(movieResponse.body()?.media!!)
                } else {
                    //Sync api media list to local db list without updating the last visited field
                    var mediasDb = mainRepository.getMediasFromDbAsList()
                    var mediasApi = movieResponse.body()?.media!!
                    for (mediaApi in mediasApi) {
                        for (mediaDb in mediasDb) {
                            if (mediaApi.trackId == mediaDb.trackId) {
                                mediaApi.lastVisited = mediaDb.lastVisited
                            }
                        }
                    }
                    mainRepository.updateMedias(mediasApi)
                }
            }
            mainListener?.hideProgressBar()
        }
    }

    fun updateLastVisit(media: Media) {
        Coroutines.inputOutput {
            mainRepository.updateLastVisit(media)
        }
    }
}