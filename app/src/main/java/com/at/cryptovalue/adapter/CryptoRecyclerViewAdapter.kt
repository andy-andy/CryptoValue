package com.at.cryptovalue.adapter

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import com.at.cryptovalue.CoinDetailsActivity
import com.at.cryptovalue.R
import com.at.cryptovalue.model.CryptoTicker
import com.at.cryptovalue.utilities.EXTRA_DETAILS_NAME
import com.at.cryptovalue.utilities.EXTRA_DETAILS_PRICE
import com.at.cryptovalue.utilities.EXTRA_DETAILS_SYMBOL

class CryptoRecyclerViewAdapter : DataBindingAdapter<CryptoTicker>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<CryptoTicker>() {
        override fun areContentsTheSame(oldItem: CryptoTicker, newItem: CryptoTicker): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CryptoTicker, newItem: CryptoTicker): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<CryptoTicker>, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnClickListener {
            val coinDetailsIntent = Intent(it.context, CoinDetailsActivity::class.java)

            val bundle = Bundle()
            bundle.putString(EXTRA_DETAILS_NAME, getItem(position).name)
            bundle.putString(EXTRA_DETAILS_SYMBOL, getItem(position).symbol)
            bundle.putString(EXTRA_DETAILS_PRICE, getItem(position).price_usd)
            coinDetailsIntent.putExtras(bundle)
            // Start coin details activity
            startActivity(it.context, coinDetailsIntent, null)
        }
    }

    override fun getItemViewType(position: Int) = R.layout.crypto_ticker_item_view
}

