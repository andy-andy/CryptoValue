package com.at.cryptovalue

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.at.cryptovalue.Utilities.EXTRA_DETAILS_NAME
import com.at.cryptovalue.Utilities.EXTRA_DETAILS_PRICE
import com.at.cryptovalue.Utilities.EXTRA_DETAILS_SYMBOL
import com.at.cryptovalue.adapter.CryptoRecyclerViewAdapter
import com.at.cryptovalue.api.CryptoTickerApiService
import com.at.cryptovalue.root.App
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.Subscriptions
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var cryptoTickerApiService: CryptoTickerApiService

    var subscription = Subscriptions.empty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponentGraph.inject(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Layout manager
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //Item animator
        recyclerView.itemAnimator = DefaultItemAnimator()
        //Item divider
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        dividerItemDecoration.setDrawable(this.getDrawable(R.drawable.item_divider))
        recyclerView.addItemDecoration(dividerItemDecoration)
        //Set fixed size
        recyclerView.setHasFixedSize(true)

        //RecyclerView onClick
        val adapter = CryptoRecyclerViewAdapter { item ->
            val coinDetailsIntent = Intent(this, CoinDetailsActivity::class.java)
            coinDetailsIntent.putExtra(EXTRA_DETAILS_NAME, item.name)
            coinDetailsIntent.putExtra(EXTRA_DETAILS_SYMBOL, item.symbol)
            coinDetailsIntent.putExtra(EXTRA_DETAILS_PRICE, item.price_usd)
            startActivity(coinDetailsIntent)
        }

        //Set new adapter
        recyclerView.adapter = adapter

        updateCryptoData(adapter)

        swipe_refresh.setOnRefreshListener({
            updateCryptoData(adapter)
        })
    }

    override fun onPause() {
        super.onPause()
        subscription.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateCryptoData(adapter: CryptoRecyclerViewAdapter) {
        swipe_refresh.isRefreshing = true
        subscription = cryptoTickerApiService.getTop200Cryptos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.data = it
                    swipe_refresh.isRefreshing = false
                }, { it.printStackTrace() })
    }
}
