package com.at.cryptovalue

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.at.cryptovalue.adapter.CryptoRecyclerViewAdapter
import com.at.cryptovalue.api.CryptoTickerApiService
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.Subscriptions
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject internal lateinit var cryptoTickerApiService: CryptoTickerApiService

    var subscription = Subscriptions.empty()

    val adapter = CryptoRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponentGraph.inject(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        updateCryptoData()

        swipe_refresh.setOnRefreshListener({
            updateCryptoData()
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

    fun updateCryptoData() {
        swipe_refresh.isRefreshing = true
        subscription = cryptoTickerApiService.getTop200Cryptos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.data = it
                    swipe_refresh.isRefreshing = false
                }, { it.printStackTrace() })
    }

}
