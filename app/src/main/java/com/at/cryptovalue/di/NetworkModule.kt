package com.at.cryptovalue.di

import android.content.Context
import com.at.cryptovalue.api.CryptoTickerApiService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
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
    fun providesClient(context: Context, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
        return OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, cacheSize))
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
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