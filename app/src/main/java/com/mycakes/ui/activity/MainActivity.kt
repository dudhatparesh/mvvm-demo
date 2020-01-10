package com.mycakes.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycakes.R
import com.mycakes.data.model.Cake
import com.mycakes.data.remote.WebServices
import com.mycakes.data.repository.CakeRepositoryImpl
import com.mycakes.ui.adapter.CakeAdapter
import com.mycakes.ui.adapter.listener.CakeClickListener
import com.mycakes.ui.dialog.CakeDetailDialog
import com.mycakes.viewmodel.MainViewModel
import com.mycakes.viewmodel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var cakeAdapter: CakeAdapter
    private val cakeClickListener: CakeClickListener = object : CakeClickListener {
        override fun onClick(cake: Cake) {
            val cakeDetailDialog = CakeDetailDialog.instance(cake)
            cakeDetailDialog.show(supportFragmentManager, "CAKE_DETAIL")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProviders.of(
            this,
            MainViewModelFactory(CakeRepositoryImpl(WebServices.instance))
        )
            .get(MainViewModel::class.java)




        viewModel.loadingState.observe(this, Observer {
            when (it) {
                is MainViewModel.LoadingState.LOADING -> displayProgressbar()
                is MainViewModel.LoadingState.SUCCESS -> displayList(it.cakes)
                is MainViewModel.LoadingState.ERROR -> displayMessageContainer(it.message)
                else -> displayMessageContainer("Unknown Error")
            }
        })
        if (viewModel.lastFetchedTime == null) {
            viewModel.fetchCakes()
        }

        btnRetry.setOnClickListener {
            viewModel.fetchCakes()
        }

    }

    private fun displayProgressbar() {
        progressbar.visibility = View.VISIBLE
        rvCakes.visibility = View.GONE
        llMessageContainer.visibility = View.GONE
    }

    private fun displayMessageContainer(message: String) {
        llMessageContainer.visibility = View.VISIBLE
        rvCakes.visibility = View.GONE
        progressbar.visibility = View.GONE
        tvMessage.text = message
    }

    private fun displayList(cakes: List<Cake>) {
        cakeAdapter.cakes.clear()
        cakeAdapter.cakes.addAll(cakes)
        cakeAdapter.notifyDataSetChanged()
        llMessageContainer.visibility = View.GONE
        rvCakes.visibility = View.VISIBLE
        progressbar.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        rvCakes.layoutManager = LinearLayoutManager(this)
        cakeAdapter = CakeAdapter(mutableListOf(), cakeClickListener)
        rvCakes.adapter = cakeAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.fetchCakes()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }

    }

}
