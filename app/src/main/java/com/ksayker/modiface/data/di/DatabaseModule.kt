package com.ksayker.modiface.data.di

import android.content.Context
import androidx.room.Room
import com.ksayker.modiface.core.annotation.AppContext
import com.ksayker.modiface.data.database.AppDatabase
import com.ksayker.modiface.data.database.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@AppContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATA_BASE_NAME).build()

    @Singleton
    @Provides
    fun provideImageDao(dataBase: AppDatabase) = dataBase.imageDao()
}