package com.at.cryptovalue.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.at.cryptovalue.R
import com.at.cryptovalue.utilities.EXTRA_DETAILS_NAME
import com.at.cryptovalue.utilities.EXTRA_DETAILS_PRICE
import com.at.cryptovalue.utilities.EXTRA_DETAILS_SYMBOL
import kotlinx.android.synthetic.main.activity_coin_details.*

class CoinDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)

        tickerName.text = intent.getStringExtra(EXTRA_DETAILS_NAME)
        tickerSymbol.text = intent.getStringExtra(EXTRA_DETAILS_SYMBOL)
        tickerPriceUsd.text = intent.getStringExtra(EXTRA_DETAILS_PRICE)
    }
}
