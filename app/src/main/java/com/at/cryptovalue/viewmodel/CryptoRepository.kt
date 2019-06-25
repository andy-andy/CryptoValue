package com.at.cryptovalue.viewmodel

import androidx.lifecycle.MutableLiveData
import com.at.cryptovalue.api.CryptoTickerApiService
import com.at.cryptovalue.model.CryptoTicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepository @Inject constructor(private val cryptoTickerApiService: CryptoTickerApiService) {

    private var cryptoTickers = mutableListOf<CryptoTicker>()
    private var mutableLiveData = MutableLiveData<List<CryptoTicker>>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    fun getMutableLiveData(): MutableLiveData<List<CryptoTicker>> {
        coroutineScope.launch {
            val request = cryptoTickerApiService.getTop200CoinsAsync()
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    cryptoTickers = response
                    mutableLiveData.value = cryptoTickers
                } catch (e: HttpException) {
                    // Log exception //
                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return mutableLiveData
    }
}