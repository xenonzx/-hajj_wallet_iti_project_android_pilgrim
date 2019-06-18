package com.example.android.pilgrim.chargeWallet

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.requests.CardFields
import kotlinx.android.synthetic.main.activity_charge_wallet.*

class ChargeWalletActivity : AppCompatActivity() {

    private lateinit var viewModel: ChargeWalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_wallet)

        val intent = intent
        val token = intent.getStringExtra("token")

        viewModel = ViewModelProviders.of(this).get(ChargeWalletViewModel::class.java)

        chargeWalletButton.setOnClickListener {
            chargeWalletButton.startAnimation()
            viewModel.chargeWallet(
                CardFields(
                    card_number.text.toString(),
                    exp_mon.text.toString(),
                    exp_year.text.toString(),
                    cvc.text.toString(),
                    amount.text.toString(),
                    currency.text.toString(),
                    pin_code.text.toString()
                ), token
            )
        }

        viewModel.response.observe(this, Observer {
            chargeWalletButton.revertAnimation()

            if (it != null) {
                Toast.makeText(this, getString(R.string.charged_succesfully), Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()

        })
    }
}
