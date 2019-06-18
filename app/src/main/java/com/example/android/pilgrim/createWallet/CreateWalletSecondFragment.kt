package com.example.android.pilgrim.createWallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.pilgrim.R
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_create_wallet_second.*

class CreateWalletSecondFragment : Fragment(), BlockingStep {
    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback!!.goToPrevStep()
    }

    override fun onSelected() {
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        val parentActivity = (activity as CreateWalletActivity)
        parentActivity.createWalletbody.dob_day = day.text.toString()
        parentActivity.createWalletbody.dob_month = Month.text.toString()
        parentActivity.createWalletbody.dob_year = year.text.toString()
        parentActivity.createWalletbody.ssn_last_numbers = ssnLastNumber.text.toString()
        parentActivity.createWalletbody.pin_code = pinCode.text.toString()

        callback!!.complete()

    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_wallet_second, container, false)
    }


}