package com.ksayker.modiface

import android.app.Application
import com.ksayker.modiface.presentation.di.ApplicationComponent
import com.ksayker.modiface.presentation.di.DaggerApplicationComponent

class App : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
            .applicationContext(this@App)
            .build()
    }
}