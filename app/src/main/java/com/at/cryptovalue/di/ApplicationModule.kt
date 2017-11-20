package com.at.cryptovalue.di

import android.content.Context
import com.at.cryptovalue.NetworkModule
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(NetworkModule::class))
class ApplicationModule(context: Context) {

    internal val context: Context = context.applicationContext

    @Provides
    internal fun providesContext() = context

}