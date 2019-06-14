package com.example.android.pilgrim.qrScanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.vendorDetails.VendorDetailsActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.qr_scanner_fragment.*


class QrScannerFragment : Fragment() {

    val TAG = "QrScannerFragment"
    private lateinit var viewModel: QrScannerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(QrScannerViewModel::class.java)
        startQRScanner()
        return inflater.inflate(com.example.android.pilgrim.R.layout.qr_scanner_fragment, container, false)
    }

    private fun startQRScanner() {
        val intentIntegrator = IntentIntegrator.forSupportFragment(this@QrScannerFragment)

        intentIntegrator.setPrompt(getString(R.string.scan_vendor_qr)).setOrientationLocked(true).setBeepEnabled(true)
            .initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "activity Scanner")

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {

                /*val vendor: Vendor? = viewModel.getVendor(result.contents)
                if (vendor != null)
                    updateView(vendor)
                else
                    Toast.makeText(context, getString(R.string.no_vendor), Toast.LENGTH_SHORT).show()
                //TODO change not found behavior
                */
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun updateView(vendor: Vendor) {
        /*tv_vendor_name.text = vendor.name
        tv_vendor_type.text = vendor.type
        Glide.with(context!!).load(vendor.logoUrl).into(iv_vendor_logo)*/

        layout_vendor_data.setOnClickListener {
            val intent = Intent(context, VendorDetailsActivity::class.java)
            intent.putExtra("vendor", vendor)
            startActivity(intent)
        }

        btn_purchase.setOnClickListener {
            //TODO
        }
    }

}
