package com.example.android.pilgrim.qrScanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.utils.Validation
import com.example.android.pilgrim.vendorDetails.VendorDetailsActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.qr_scanner_fragment.*


class QrScannerFragment : Fragment() {

    val TAG = "QrScannerFragment"
    private lateinit var viewModel: QrScannerViewModel

    private var token: String? = null

    private var vendorId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(QrScannerViewModel::class.java)


        val intent = activity?.intent
        token = intent?.getStringExtra("token")

        startQRScanner()

        return inflater.inflate(
            com.example.android.pilgrim.R.layout.qr_scanner_fragment,
            container,
            false
        )
    }

    private fun startQRScanner() {
        val intentIntegrator = IntentIntegrator.forSupportFragment(this@QrScannerFragment)

        intentIntegrator.setPrompt(getString(R.string.scan_vendor_qr)).setOrientationLocked(true)
            .setBeepEnabled(true)
            .initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "activity Scanner")

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {

                viewModel.getVendor("Token " + token, result.contents, context!!)

                viewModel.vendor.observe(this, Observer { vendor ->
                    progress_bar.visibility = View.INVISIBLE
                    if (vendor != null) {
                        layout_store_data.visibility = View.VISIBLE
                        updateView(vendor)
                        vendorId = vendor.id
                    }
                })
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun updateView(vendor: Vendor) {
        tv_vendor_name.text = vendor.storeName
        tv_vendor_type.text = vendor.category
        Glide.with(context!!).load(vendor.image).into(iv_vendor_logo)

        layout_vendor_data.setOnClickListener {

            viewModel.getVendorDetails("Token " + token, vendor.id!!)

            viewModel.vendorDetails.observe(this, Observer { vendorDetails ->

                if (vendorDetails != null) {
                    val intent = Intent(context, VendorDetailsActivity::class.java)
                    intent.putExtra("vendor", vendorDetails)
                    startActivity(intent)
                } else
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            })

        }

        //TODO handel if less than 10 cents
        btn_purchase.setOnClickListener {
            if (Validation.isFieldValid(context!!, et_money) &&
                Validation.isFieldValid(context!!, et_pin)
            ) {
                btn_purchase.startAnimation()
                viewModel.transferMoney(
                    "Token " + token,
                    vendorId!!,
                    et_money.text.toString(),
                    et_pin.text.toString(),
                    "usd"
                )

                viewModel.transactionResult.observe(this, Observer { result ->
                    btn_purchase.revertAnimation()

                    if (result == null)
                        Toast.makeText(
                            context,
                            getString(R.string.error),
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            result,
                            Toast.LENGTH_SHORT
                        ).show()
                })
            }
        }
    }


}
