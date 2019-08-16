package com.lambdaschool.sprint2_challenge

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.items_list.view.*

class ItemsListAdapter(val itemsList: MutableList<Items>): RecyclerView.Adapter<ItemsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.items_list, p0, false))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val items = itemsList[p1]
        p0.itemImageview.setImageResource(items.imageID) //using resource because our model has an int
        p0.itemNameView.text = items.name
        p0.bindPurchasedModel(items)


        p0.itemParent.setOnClickListener{
            if(items.isPurchase) {
                items.isPurchase = false
                notifyItemChanged(p1)
            } else {
                items.isPurchase = true
                notifyItemChanged(p1)
            }

        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemImageview: ImageView = view.item_image_view
        val itemNameView: TextView = view.item_name_view
        val itemParent: LinearLayout = view.item_parent

        //separate funciton to bind the isPurchase param and to set the color of the view based on the value of isPurchase
        fun bindPurchasedModel(items: Items) {

            if(items.isPurchase)
                itemParent.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
            else
                itemParent.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
        }
    }
}