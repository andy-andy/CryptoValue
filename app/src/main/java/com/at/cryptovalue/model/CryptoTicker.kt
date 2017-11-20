package com.at.cryptovalue.model

data class CryptoTicker(
        val id: String,
        val name: String,
        val symbol: String,
        val price_usd: String
)