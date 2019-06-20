package com.at.cryptovalue

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

    private var subscription = Subscriptions.empty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponentGraph.inject(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Layout manager
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //Item animator
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        //Item divider
        val dividerItemDecoration = androidx.recyclerview.widget.DividerItemDecoration(this, layoutManager.orientation)
        dividerItemDecoration.setDrawable(this.getDrawable(R.drawable.item_divider))
        recyclerView.addItemDecoration(dividerItemDecoration)
        //Set fixed size
        recyclerView.setHasFixedSize(true)

        val mAdapter = CryptoRecyclerViewAdapter()
        recyclerView.adapter = mAdapter

        updateCryptoData(mAdapter)

        swipe_refresh.setOnRefreshListener {
            updateCryptoData(mAdapter)
        }
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
        subscription = cryptoTickerApiService.getTop200Coins()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let(adapter::submitList)
                    swipe_refresh.isRefreshing = false
                }, { it.printStackTrace() })
    }
}
