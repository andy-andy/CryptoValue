package com.at.cryptovalue.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.at.cryptovalue.R
import com.at.cryptovalue.adapter.CryptoRecyclerViewAdapter
import com.at.cryptovalue.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        // Layout manager
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        // Item animator
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        //Item divider
        val dividerItemDecoration = androidx.recyclerview.widget.DividerItemDecoration(this, layoutManager.orientation)
        dividerItemDecoration.setDrawable(this.getDrawable(R.drawable.item_divider))
        recyclerView.addItemDecoration(dividerItemDecoration)
        //Set fixed size
        recyclerView.setHasFixedSize(true)

        val adapter = CryptoRecyclerViewAdapter()
        recyclerView.adapter = adapter

        updateCryptoData(adapter)

        swipe_refresh.setOnRefreshListener {
            updateCryptoData(adapter)
        }
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

    private fun updateCryptoData(adapter: CryptoRecyclerViewAdapter) {
        swipe_refresh.isRefreshing = true
        mainViewModel.allCryptoTickers.observe(this, Observer { cryptoTickerList ->
            cryptoTickerList?.let {
                it.let(adapter::submitList)
                swipe_refresh.isRefreshing = false
            }
        })
    }
}
