package com.at.cryptovalue

import com.at.cryptovalue.api.CryptoTickerApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun providesCryptoTickerApiService(): CryptoTickerApiService {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.coinmarketcap.com/v1/")
                .build()
        return retrofit.create(CryptoTickerApiService::class.java)
    }
}