package com.example.android.pilgrim.home

import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.pilgrim.R
import com.example.android.pilgrim.filter.FilterActivity
import com.example.android.pilgrim.model.FindNearestVendorsRequest
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.vendorDetails.VendorDetailsActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private var mAdapter: VendorPrevAdapter? = null

    private lateinit var viewModel: HomeViewModel

    private var token: String? = null

    private lateinit var rootView: View
    val FILTER_REQUEST_CODE = 1

    private var filter: Int? = 0

    private var locationManager: LocationManager? = null

    val TAG = "HomeFragment"

    private var lat: Double? = null
    private var lng: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView =
            inflater.inflate(com.example.android.pilgrim.R.layout.fragment_home, container, false)

        val intent = activity?.intent
        token = intent?.getStringExtra("token")

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        rootView.rv_vendor_prev.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progress_bar.visibility = View.VISIBLE

        //locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager?;

        viewModel.vendors.observe(this, Observer { vendors ->

            if (vendors != null) {
                if (vendors.size > 0) {
                    mAdapter = VendorPrevAdapter(vendors, context) { vendor: Vendor ->
                        val intent = Intent(context, VendorDetailsActivity::class.java)
                        intent.putExtra("vendor", vendor)
                        startActivity(intent)
                    }
                    rootView.rv_vendor_prev.adapter = mAdapter
                } else {
                    tv_no_vendors.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }

            progress_bar.visibility = View.INVISIBLE

        })
    }

    override fun onStart() {
        super.onStart()
        getVendors()
        /*try {
            // Request location updates
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                600000,
                1000f,
                locationListener
            )
        } catch (ex: SecurityException) {
            Log.d(TAG, "Security Exception, no location available");
        }*/
    }

    fun getVendors() {
        viewModel.getVendors(
            "Token $token",
            FindNearestVendorsRequest(
                50.0001f.toString(),
                45.0001f.toString(),
                filter.toString(),
                1000f.toString()
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.home, menu)

        /*val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(com.example.android.pilgrim.R.id.action_search)
            .getActionView() as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )*/
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> true
            R.id.action_filter -> {
                val intent = Intent(context, FilterActivity::class.java)
                intent.putExtra("ChosenFilter", filter)

                startActivityForResult(
                    intent,
                    FILTER_REQUEST_CODE
                )
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILTER_REQUEST_CODE -> {
                if (data != null) {
                    filter = data.extras.getInt("filter", 0)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /* private val locationListener: LocationListener = object : LocationListener {
         override fun onLocationChanged(location: Location) {
             lat = location.latitude
             lng = location.longitude
             getVendors()
         }

         override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
         override fun onProviderEnabled(provider: String) {}
         override fun onProviderDisabled(provider: String) {}
     }
 */
}
