package com.ksayker.modiface.data.provider

import com.ksayker.modiface.domain.provider.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

    override val defaultDispatcher = Dispatchers.Default

    override val uiDispatcher = Dispatchers.Main

    override val ioDispatcher = Dispatchers.IO
}