package com.example.android.pilgrim.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.pojo.Vendor
import kotlinx.android.synthetic.main.item_view_vendor_prev.view.*

/**
 * Created by Toka on 2019-06-02.
 */
class VendorPrevAdapter constructor(
    val vendors: List<Vendor>,
    val context: Context?,
    val clickListener: (Vendor, Int) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_view_vendor_prev,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return vendors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.name?.text = vendors.get(position).username
        /*holder?.distance?.text =
            vendors.get(position).distance.toString() + " " + context?.getString(R.string.feet_away)
        holder?.type?.text = vendors.get(position).type
        holder?.ratingBar?.rating = vendors.get(position).rating.toFloat()

        if (context != null) {
            Glide.with(context).load(vendors.get(position).logoUrl).into(holder?.logo)
        }*/

        holder?.itemView?.setOnClickListener { clickListener(vendors.get(position), position) }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.tv_vendor_name
    val distance = view.tv_distance
    val ratingBar = view.rb_rating
    val type = view.tv_type
    val logo = view.iv_vendor_logo
}