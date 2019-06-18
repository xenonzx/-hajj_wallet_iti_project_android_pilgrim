package com.example.android.pilgrim.createWallet

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.requests.CreateWalletBody
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.activity_create_wallet.*

class CreateWalletActivity : AppCompatActivity(), StepperLayout.StepperListener {

    private lateinit var viewModel: CreateWalletViewModel
    lateinit var createWalletbody: CreateWalletBody


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_wallet)


        createWalletbody = CreateWalletBody(
            null, null, null, null, null,
            null, null, null, null, null
        )
        viewModel = ViewModelProviders.of(this).get(CreateWalletViewModel::class.java)
        stepperLayout!!.adapter = MyStepperAdapter(supportFragmentManager, this)
        stepperLayout!!.setListener(this)
    }

    override fun onStepSelected(newStepPosition: Int) {
    }

    override fun onError(verificationError: VerificationError?) {
    }

    override fun onReturn() {
    }


    override fun onCompleted(completeButton: View?) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Creating Wallet")
        progressDialog.show()
        val intent = intent
        val token = intent.getStringExtra("token")

        viewModel.CreateWallet(createWalletbody, this, token)
        viewModel.response.observe(this, Observer {
            if (it != null) {
                Toast.makeText(
                    this@CreateWalletActivity,
                    getString(R.string.create_wallet_successful),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
            progressDialog.dismiss()
        })
    }
}