package com.mycakes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycakes.R
import com.mycakes.data.model.Cake
import com.mycakes.ui.adapter.listener.CakeClickListener
import com.mycakes.ui.adapter.viewholder.CakeViewHolder

class CakeAdapter constructor(val cakes: MutableList<Cake>, private val cakeClickListener: CakeClickListener) :
    RecyclerView.Adapter<CakeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CakeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cake, parent, false))

    override fun getItemCount() = cakes.size

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {
        holder.bindItem(cakes[position])
        holder.itemView.setOnClickListener {
            cakeClickListener.onClick(cakes[holder.adapterPosition])
        }
    }
}
