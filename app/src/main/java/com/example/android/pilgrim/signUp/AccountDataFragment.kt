package com.example.android.pilgrim.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.pilgrim.utils.Validation.Companion.isEmailValid
import com.example.android.pilgrim.utils.Validation.Companion.isFieldValid
import com.example.android.pilgrim.utils.Validation.Companion.isPasswordValid
import com.example.android.pilgrim.utils.Validation.Companion.passwordsMatch
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_account_data.*


class AccountDataFragment : Fragment(), BlockingStep {

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        if (isUserDataValid()) {
            val parentActivity = (activity as SignUpActivity)
            parentActivity.pilgrim.username = username.text.toString()
            parentActivity.pilgrim.email = email.text.toString()
            parentActivity.pilgrim.password1 = password1.text.toString()
            parentActivity.pilgrim.password2 = password2.text.toString()

            callback!!.goToNextStep()
        }
    }

    override fun onSelected() {
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

        return inflater.inflate(
            com.example.android.pilgrim.R.layout.fragment_account_data,
            container,
            false
        )
    }

    private fun isUserDataValid(): Boolean {
        return isFieldValid(context!!, username) && isFieldValid(context!!, email) &&
                isEmailValid(context!!, email) && isPasswordValid(context!!, password1) &&
                isPasswordValid(context!!, password2) && passwordsMatch(
            context!!,
            password1,
            password2
        )
    }

}
