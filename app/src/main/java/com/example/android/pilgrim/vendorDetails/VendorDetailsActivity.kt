package com.example.android.pilgrim.vendorDetails

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.pojo.Vendor

class VendorDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: VendorDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(VendorDetailsViewModel::class.java)

        //TODO does intent has all of the vendor data?


        val vendor = intent.extras.get("vendor") as Vendor
        //this.title = vendor.name
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
