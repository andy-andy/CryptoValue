package com.at.cryptovalue.api

import com.at.cryptovalue.model.CryptoTicker
import retrofit2.http.GET
import rx.Observable

interface CryptoTickerApiService {

    @GET("ticker/?limit=200")
    fun getTop200Cryptos(): Observable<ArrayList<CryptoTicker>>
}