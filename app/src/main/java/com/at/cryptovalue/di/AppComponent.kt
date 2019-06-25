package com.at.cryptovalue.di

import com.at.cryptovalue.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Injects application dependencies.
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ViewModelModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}

