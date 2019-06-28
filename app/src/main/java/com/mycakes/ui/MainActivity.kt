package com.mycakes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycakes.R
import com.mycakes.data.model.Cake
import com.mycakes.ui.adapter.CakeAdapter
import com.mycakes.ui.adapter.listener.CakeClickListener
import com.mycakes.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var cakeAdapter: CakeAdapter
    val cakeClickListener: CakeClickListener = object : CakeClickListener {
        override fun onClick(cake: Cake) {
            //Display Cake Description
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.cakes.observe(this, Observer {
            if (it.isNotEmpty()) {
                cakeAdapter.cakes.clear()
                cakeAdapter.cakes.addAll(it)
                cakeAdapter.notifyDataSetChanged()
            }
        })


    }

    private fun setupRecyclerView() {
        rvCakes.layoutManager = LinearLayoutManager(this)
        cakeAdapter = CakeAdapter(mutableListOf(), cakeClickListener)
        rvCakes.adapter = cakeAdapter
    }

}
