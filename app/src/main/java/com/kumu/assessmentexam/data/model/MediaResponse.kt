package com.kumu.assessmentexam.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */

data class MediaResponse(
    var resultCount: Int,
    @SerializedName("results")
    val media: List<Media>
)