package com.at.cryptovalue

import android.app.Application
import com.at.cryptovalue.di.ApplicationComponent
import com.at.cryptovalue.di.ApplicationModule
import com.at.cryptovalue.di.DaggerApplicationComponent

class App : Application() {

    companion object {
        // platformStatic allow access it from java code
        @JvmStatic lateinit var appComponentGraph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponentGraph = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        appComponentGraph.inject(this)
    }
}