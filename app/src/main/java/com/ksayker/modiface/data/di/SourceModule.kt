package com.ksayker.modiface.data.di

import com.ksayker.modiface.data.source.FileSourceImpl
import com.ksayker.modiface.data.source.FilterSourceImpl
import com.ksayker.modiface.data.source.ImageSourceImpl
import com.ksayker.modiface.data.source.UnsplashSourceImpl
import com.ksayker.modiface.domain.source.FileSource
import com.ksayker.modiface.domain.source.FilterSource
import com.ksayker.modiface.domain.source.ImageSource
import com.ksayker.modiface.domain.source.UnsplashSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SourceModule {

    @Singleton
    @Binds
    fun bindImageSource(source: ImageSourceImpl): ImageSource

    @Singleton
    @Binds
    fun bindFilterSource(source: FilterSourceImpl): FilterSource

    @Singleton
    @Binds
    fun bindFileSource(source: FileSourceImpl): FileSource

    @Singleton
    @Binds
    fun bindUnsplashSource(source: UnsplashSourceImpl): UnsplashSource
}