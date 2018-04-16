package com.at.cryptovalue.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class ApplicationModule(private val context: Context) {

    @Provides
    internal fun providesApplicationContext(): Context = context

}