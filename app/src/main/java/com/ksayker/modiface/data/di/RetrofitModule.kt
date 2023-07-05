package com.ksayker.modiface.data.di

import com.ksayker.modiface.data.remote.BASE_URL
import com.ksayker.modiface.data.remote.UnsplashApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApiService = retrofit.create(UnsplashApiService::class.java)
}