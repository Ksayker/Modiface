package com.ksayker.modiface.data.di

import com.ksayker.modiface.data.provider.DispatcherProviderImpl
import com.ksayker.modiface.domain.provider.DispatcherProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ProviderModule {

    @Singleton
    @Binds
    fun bindDispatcherProvider(provider: DispatcherProviderImpl): DispatcherProvider
}