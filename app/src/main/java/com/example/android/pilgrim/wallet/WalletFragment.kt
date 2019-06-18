package com.example.android.pilgrim.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R

class WalletFragment : Fragment() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WalletViewModel::class.java)
        /* viewModel.getResult(textToken)
         viewModel.getCheckResult.observe(this, Observer {
             balanceNumber.text = it.totalBalance.toString()
         })
         createWallet.setOnClickListener {
             val intent = Intent(activity, CreateWalletActivity::class.java)
             activity!!.startActivity(intent)
         }*/
    }

}
