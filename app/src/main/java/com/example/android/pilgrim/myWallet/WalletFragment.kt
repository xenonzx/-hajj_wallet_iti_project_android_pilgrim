package com.example.android.pilgrim.myWallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.createWallet.CreateWalletActivity
import kotlinx.android.synthetic.main.fragment_wallet.*

class WalletFragment : Fragment() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        Log.i("WalletFragment", "onstart")
        val intent = activity?.intent
        val token = intent?.getStringExtra("token")

        viewModel = ViewModelProviders.of(this).get(WalletViewModel::class.java)
        viewModel.getResult(token!!)
        viewModel.checkResult.observe(this, Observer {
            if (it == null) {
                layout_create_wallet.visibility = View.VISIBLE
                layout_balance.visibility = View.INVISIBLE
            } else {
                layout_create_wallet.visibility = View.INVISIBLE
                layout_balance.visibility = View.VISIBLE
                balanceNumber.text = it.totalBalance.toString()
            }
        })
        createWallet.setOnClickListener {
            val intent = Intent(activity, CreateWalletActivity::class.java)
            intent.putExtra("token", token)
            activity!!.startActivity(intent)
        }

        chargeWalletButton.setOnClickListener {

        }
    }

}
