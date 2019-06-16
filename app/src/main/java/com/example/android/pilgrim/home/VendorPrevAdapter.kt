package com.example.android.pilgrim.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.pojo.Vendor
import kotlinx.android.synthetic.main.item_view_vendor_prev.view.*

/**
 * Created by Toka on 2019-06-02.
 */
class VendorPrevAdapter constructor(
    val vendors: List<Vendor>,
    val context: Context?,
    val clickListener: (Vendor) -> Unit
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

        var distance = (vendors.get(position).distance.toDouble() * 100 * 1000)
        distance = String.format("%.2f", distance).toDouble()

        holder?.name?.text = vendors.get(position).username
        holder?.distance?.text = distance.toString() + " " + context?.getString(R.string.metre)

        holder?.type?.text = vendors.get(position).category

        if (context != null && !vendors.get(position).image.isEmpty()) {
            Glide.with(context).load(vendors.get(position).image).into(holder?.logo)
        }

        holder?.itemView?.setOnClickListener { clickListener(vendors[position]) }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.tv_vendor_name
    val distance = view.tv_distance
    val type = view.tv_type
    val logo = view.iv_vendor_logo
}