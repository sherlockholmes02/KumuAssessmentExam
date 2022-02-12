package com.kumu.assessmentexam.main

import com.kumu.assessmentexam.data.model.Media

/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */
interface MainInterface {
    fun onSuccessfulFetch(media: List<Media>)
    fun onFailureFetch(message: String)
    fun showProgressBar()
    fun hideProgressBar()
}