package com.example.android.pilgrim.signUp

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter


/**
 * Created by Toka on 2019-06-10.
 */
class MyStepperAdapter(fm: FragmentManager, context: Context) :
    AbstractFragmentStepAdapter(fm, context) {

    override fun createStep(position: Int): Step? {
        when (position) {
            0 -> {
                val step1 = AccountDataFragment();
                return step1;
            }
            1 -> {
                val step2 = UserDataFragment();
                return step2;
            }
        }
        return null
    }

    override fun getCount(): Int {
        return 2
    }

}