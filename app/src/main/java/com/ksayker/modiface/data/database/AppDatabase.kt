package com.ksayker.modiface.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ksayker.modiface.data.database.dao.ImageDao
import com.ksayker.modiface.domain.entity.Image

const val DATA_BASE_NAME = "database"

@Database(version = 1, entities = [Image::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}