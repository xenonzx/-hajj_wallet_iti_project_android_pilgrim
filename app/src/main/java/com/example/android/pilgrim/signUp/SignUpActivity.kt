package com.example.android.pilgrim.signUp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.home.HomeActivity
import com.example.android.pilgrim.model.pojo.Pilgrim
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity(), StepperLayout.StepperListener {

    lateinit var pilgrim: Pilgrim

    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.pilgrim.R.layout.activity_sign_up)

        pilgrim = Pilgrim(
            null, null, null, null, null,
            null, null, null, null, null, null
        )

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

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
        progressDialog.setTitle(getString(R.string.creating_account))
        progressDialog.show()

        viewModel.signUp(pilgrim, this)

        viewModel.response.observe(this, Observer { response ->
            progressDialog.dismiss()
            if (response.token != null) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("token", response.token)
                intent.putExtra("user", response.user)
                startActivity(intent)
                finish()
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}