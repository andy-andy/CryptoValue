package com.at.cryptovalue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.at.cryptovalue.model.CryptoTicker
import javax.inject.Inject

class MainViewModel @Inject constructor(private val cryptoRepository: CryptoRepository) : ViewModel() {

    val allCryptoTickers: LiveData<List<CryptoTicker>> get() = cryptoRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        cryptoRepository.completableJob.cancel()
    }
}