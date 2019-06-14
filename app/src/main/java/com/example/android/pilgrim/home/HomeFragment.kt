package com.example.android.pilgrim.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.vendorDetails.VendorDetailsActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private var mAdapter: VendorPrevAdapter? = null

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.example.android.pilgrim.R.layout.fragment_home, container, false)

        val intent = activity?.intent
        val token = intent?.getStringExtra("token")

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        view.rv_vendor_prev.layoutManager = LinearLayoutManager(context)

        viewModel.getVendors(token!!, "1", 45.0f, 50.0f, 100f)

        viewModel.vendors.observe(this, Observer { vendors ->
            mAdapter = VendorPrevAdapter(vendors, context) { vendor: Vendor, position: Int ->
                val intent = Intent(context, VendorDetailsActivity::class.java)
                intent.putExtra("vendor", vendor)
                startActivity(intent)
            }
            view.rv_vendor_prev.adapter = mAdapter
        })

        return view
    }

}
