package com.example.android.pilgrim.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.responses.TransactionsResponse
import kotlinx.android.synthetic.main.transactions_fragment.*

class TransactionsFragment : Fragment() {

    private lateinit var viewModel: TransActionsViewModel
    private lateinit var transactionsArrayAdapter: TransactionsArrayAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.transactions)
        return inflater.inflate(R.layout.transactions_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val textToken = activity!!.intent.getStringExtra("token")

        viewModel = ViewModelProviders.of(this).get(TransActionsViewModel::class.java)
        viewModel.getTransactions(textToken)
        viewModel.transactions.observe(this, Observer {
            if (it == null) {
                recycler.visibility = View.INVISIBLE
                empty_view.visibility = View.VISIBLE
            } else {
                transactionsArrayAdapter =
                    TransactionsArrayAdapter(context!!, it as ArrayList<TransactionsResponse>)
                recycler.adapter = transactionsArrayAdapter
                recycler.visibility = View.VISIBLE
                empty_view.visibility = View.GONE
            }


        })

    }

}
