package com.example.android.pilgrim.transactions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.responses.TransactionsResponse


class TransactionsArrayAdapter(
    var context: Context,
    internal var transActionsData: ArrayList<TransactionsResponse>
) : RecyclerView.Adapter<TransactionsArrayAdapter.myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.payment_list_item, parent, false)
        return myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transActionsData.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var data = transActionsData[position]
        var date = data.timeStamp.substring(0, 10)
        holder.pilgrimUserName.text = data.pilgrimUsername
        holder.Time.text = date
        holder.money.text = (data.moneyPaid / 100.0).toString() + " $"
    }


    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pilgrimUserName: TextView = itemView.findViewById(R.id.companyTitle)
        var Time: TextView = itemView.findViewById(R.id.subtitle)
        var money: TextView = itemView.findViewById(R.id.money)
    }
}
