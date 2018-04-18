package com.at.cryptovalue.di

import com.at.cryptovalue.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(target: MainActivity)

}