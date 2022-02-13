package com.kumu.assessmentexam.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kumu.assessmentexam.data.model.Media

@Dao
interface MediaDao {

    @Query(
        """
            SELECT *
            FROM medias
        """
    )
    fun get(): List<Media>

    @Query("SELECT * FROM medias")
    fun getMedias(): LiveData<List<Media>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMedias(medias: List<Media>)
}