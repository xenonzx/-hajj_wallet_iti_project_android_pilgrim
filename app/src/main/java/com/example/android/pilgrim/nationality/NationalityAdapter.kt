package com.example.android.pilgrim.nationality

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.responses.CategoryNationalityResponse
import kotlinx.android.synthetic.main.item_view_nationality.view.*

/**
 * Created by Toka on 2019-06-02.
 */
class NationalityAdapter constructor(
    private val nationalities: List<CategoryNationalityResponse>,
    val context: Context?,
    val clickListener: (CategoryNationalityResponse, Int) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_view_nationality,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return nationalities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.name?.text = nationalities.get(position).name

        holder?.itemView?.setOnClickListener {
            clickListener(
                nationalities.get(position),
                position
            )
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.tv_nationality
}