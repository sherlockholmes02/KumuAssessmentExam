package com.kumu.assessmentexam.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kumu.assessmentexam.data.daos.MediaDao
import com.kumu.assessmentexam.data.model.Media

@Database(
    version = 1,
    entities = [
        Media::class,
    ]
)
abstract class MediaDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private lateinit var INSTANCE: MediaDatabase

        fun init(context: Context) {
            if (!::INSTANCE.isInitialized)
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MediaDatabase::class.java,
                    "media_database"
                ).build()
        }

        fun getInstance() = INSTANCE

    }

    abstract fun mediaDao(): MediaDao
}