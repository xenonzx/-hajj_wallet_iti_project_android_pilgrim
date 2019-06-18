package com.example.android.pilgrim.createWallet

/**
 * Created by Toka on 2019-06-18.
 */
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter

class MyStepperAdapter(fm: FragmentManager, context: Context) :
    AbstractFragmentStepAdapter(fm, context) {
    override fun getCount(): Int {
        return 2
    }

    override fun createStep(position: Int): Step? {
        when (position) {
            0 -> {
                return CreateWalletFirstFragment()
            }
            1 -> {
                return CreateWalletSecondFragment()
            }
        }
        return null
    }
}