package com.at.cryptovalue.di

import com.at.cryptovalue.App
import com.at.cryptovalue.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: App)

    fun inject(target: MainActivity)

}