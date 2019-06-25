package com.at.cryptovalue.di

import android.app.Application
import com.at.cryptovalue.App
import com.at.cryptovalue.view.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
internal abstract class AppModule {

    @Binds
    @Singleton
    internal abstract fun app(app: App): Application

    /**
     * Provides the injector for the [MainActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @ContributesAndroidInjector(modules = [])
    internal abstract fun mainActivityInjector(): MainActivity
}
