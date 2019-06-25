package com.at.cryptovalue.di

import com.at.cryptovalue.App
import com.at.cryptovalue.api.CryptoTickerApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesClient(app: App, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
        return OkHttpClient.Builder()
                .cache(Cache(app.cacheDir, cacheSize))
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.coinmarketcap.com/v1/")
                .build()
    }

    @Provides
    @Singleton
    internal fun providesCryptoTickerApiService(retrofit: Retrofit): CryptoTickerApiService {
        return retrofit.newBuilder().build().create(CryptoTickerApiService::class.java)
    }
}