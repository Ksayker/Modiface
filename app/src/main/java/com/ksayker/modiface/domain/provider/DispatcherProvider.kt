package com.ksayker.modiface.domain.provider

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val defaultDispatcher: CoroutineDispatcher

    val uiDispatcher: CoroutineDispatcher

    val ioDispatcher: CoroutineDispatcher
}
