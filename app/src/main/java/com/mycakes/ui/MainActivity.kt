package com.mycakes.ui

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
import com.mycakes.viewmodel.MainViewModel
import com.mycakes.viewmodel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var cakeAdapter: CakeAdapter
    private val cakeClickListener: CakeClickListener = object : CakeClickListener {
        override fun onClick(cake: Cake) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProviders.of(this, MainViewModelFactory(CakeRepositoryImpl(WebServices.instance)))
            .get(MainViewModel::class.java)
        viewModel.cakes.observe(this, Observer {
            cakeAdapter.cakes.clear()
            cakeAdapter.cakes.addAll(it)
            cakeAdapter.notifyDataSetChanged()

        })

        viewModel.errorMessage.observe(this, Observer {
            tvMessage.text = it
        })

        viewModel.loadingState.observe(this, Observer {
            when (it) {
                MainViewModel.LoadingState.LOADING -> displayProgressbar()
                MainViewModel.LoadingState.SUCCESS -> displayList()
                MainViewModel.LoadingState.ERROR -> displayMessageContainer()
                else -> displayMessageContainer()
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

    private fun displayMessageContainer() {
        llMessageContainer.visibility = View.VISIBLE
        rvCakes.visibility = View.GONE
        progressbar.visibility = View.GONE
    }

    private fun displayList() {

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
