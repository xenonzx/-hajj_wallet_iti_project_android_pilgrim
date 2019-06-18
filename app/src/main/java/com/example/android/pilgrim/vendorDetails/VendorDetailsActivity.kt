package com.example.android.pilgrim.vendorDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.android.pilgrim.model.pojo.Vendor
import kotlinx.android.synthetic.main.content_vendor_details.*


class VendorDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: VendorDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.pilgrim.R.layout.activity_vendor_details)

        val toolbar = findViewById<Toolbar>(com.example.android.pilgrim.R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(VendorDetailsViewModel::class.java)

        //TODO does intent has all of the vendor data?


        val vendor = intent.extras.get("vendor") as Vendor
        if (vendor != null) {
            store_name.text = vendor.username
            phone_number.text = vendor.phoneNumber
            category.text = vendor.category
            if (!vendor.image.isNullOrEmpty()) {
                Glide.with(this).load(vendor.image).into(image)
            }


            if (vendor.phoneNumber.isNullOrEmpty())
                callButton.isEnabled = false
            else {
                callButton.isEnabled = true
                callButton.setOnClickListener {
                    val intent =
                        Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", vendor.phoneNumber, null))
                    startActivity(intent)
                }
            }

            navigateButton.setOnClickListener {
                val geoUri =
                    "http://maps.google.com/maps?q=loc:${vendor.lat},${vendor.long} (${vendor.storeName})"

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
                startActivity(intent)
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
