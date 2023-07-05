package com.ksayker.modiface.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksayker.modiface.domain.entity.DATE_COLUMN
import com.ksayker.modiface.domain.entity.IMAGE_TABLE_NAME
import com.ksayker.modiface.domain.entity.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(image: Image)

    @Delete
    suspend fun deleteImage(image: Image)

    @Query("SELECT * FROM $IMAGE_TABLE_NAME ORDER BY $DATE_COLUMN DESC")
    fun getImages(): Flow<List<Image>>
}