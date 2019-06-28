package com.mycakes.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mycakes.data.model.Cake
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cake.view.*

class CakeViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(cake: Cake) {
        itemView.tvCakeTitle.text = cake.title
        Picasso.get().load(cake.image).into(itemView.ivCakeImage)
    }
}