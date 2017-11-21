package com.at.cryptovalue.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.at.cryptovalue.R
import com.at.cryptovalue.model.CryptoTicker
import kotlinx.android.synthetic.main.crypto_ticker_item_view.view.*

class CryptoRecyclerViewAdapter(val cryptoTickers: ArrayList<CryptoTicker>) :
        RecyclerView.Adapter<CryptoRecyclerViewAdapter.CryptoHolder>() {

    override fun getItemCount() = cryptoTickers.size

    override fun onBindViewHolder(holder: CryptoRecyclerViewAdapter.CryptoHolder, position: Int) = holder.bind(cryptoTickers[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoRecyclerViewAdapter.CryptoHolder {
        val inflatedView = parent.inflate(R.layout.crypto_ticker_item_view, false)
        return CryptoHolder(inflatedView)
    }

    class CryptoHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(item: CryptoTicker) = with(itemView) {
            tickerName.text = item.name
            tickerSymbol.text = item.symbol
            tickerPriceUsd.text = item.price_usd
//            setOnClickListener { listener(item) }
        }
    }

}

private fun ViewGroup.inflate(recycler_crypto_item_view: Int, b: Boolean): View {
    return LayoutInflater.from(context).inflate(recycler_crypto_item_view, this, b)
}
