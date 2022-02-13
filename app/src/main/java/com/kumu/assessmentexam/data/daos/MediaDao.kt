package com.kumu.assessmentexam.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Query("SELECT COUNT(trackId) FROM medias")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMedias(medias: List<Media>)

    @Update
    fun update(media: Media)

    @Update(entity = Media::class)
    fun updateMedias(medias: List<Media>)
}