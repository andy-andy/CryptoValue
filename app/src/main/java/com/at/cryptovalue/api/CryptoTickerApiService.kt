package com.at.cryptovalue.api

import com.at.cryptovalue.model.CryptoTicker
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CryptoTickerApiService {

    @GET("ticker/?limit=200")
    fun getTop200CoinsAsync(): Deferred<ArrayList<CryptoTicker>>
}