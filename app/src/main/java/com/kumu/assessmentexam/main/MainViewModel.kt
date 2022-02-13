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
                mainRepository.saveMedias(movieResponse.body()?.media!!)
            }
            mainListener?.hideProgressBar()
        }
    }
}