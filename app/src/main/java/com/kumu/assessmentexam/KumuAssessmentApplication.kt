package com.kumu.assessmentexam

import android.app.Application
import com.kumu.assessmentexam.data.MediaDatabase

/**
 * Created by Darryl Dave P. de Castro on 13/02/2022.
 */
class KumuAssessmentApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MediaDatabase.init(this)
    }
}