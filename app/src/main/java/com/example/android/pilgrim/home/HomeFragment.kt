package com.example.android.pilgrim.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.pilgrim.filter.FilterActivity
import com.example.android.pilgrim.model.FindNearestVendorsRequest
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.vendorDetails.VendorDetailsActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private var mAdapter: VendorPrevAdapter? = null

    private lateinit var viewModel: HomeViewModel

    private var token: String? = null

    private lateinit var rootView: View
    val FILTER_REQUEST_CODE = 1

    private var filter: Int? = 0
    private var lat: Double? = null
    private var lng: Double? = null

    val TAG = "HomeFragment"


    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocation: Location? = null
    private var mLocationManager: LocationManager? = null

    private var mLocationRequest: LocationRequest? = null

    private var locationManager: LocationManager? = null
    private val isLocationEnabled: Boolean
        get() {
            locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }

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

        mGoogleApiClient = GoogleApiClient.Builder(context!!)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        mLocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        checkLocation() //check

        return rootView
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.activity!!, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1
            )
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }


        startLocationUpdates()

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

        if (mLocation == null) {
            startLocationUpdates()
        }
        if (mLocation != null) {
            lat = mLocation!!.latitude
            lng = mLocation!!.longitude
            getVendors()
        } else {
            Toast.makeText(context, "Location not Detected", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onConnectionSuspended(i: Int) {
        Log.i(TAG, "Connection Suspended")
        mGoogleApiClient!!.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode())
    }

    override fun onStart() {
        super.onStart()
        if (mGoogleApiClient != null) {
            mGoogleApiClient!!.connect()
        }
    }


    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient!!.isConnected()) {
            mGoogleApiClient!!.disconnect()
        }
    }

    @SuppressLint("MissingPermission")
    protected fun startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(0)
            .setFastestInterval(0)
            .setSmallestDisplacement(1f)
        // Request location updates
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient,
            mLocationRequest, this
        )
        Log.d("reque", "--->>>>")
    }

    override fun onLocationChanged(location: Location) {

        /*val msg = "Updated Location: " +
                java.lang.Double.toString(location.latitude) + "," +
                java.lang.Double.toString(location.longitude)

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()*/
        // You can now create a LatLng Object for use with maps
        lat = location.latitude
        lng = location.longitude
        getVendors()
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled)
            showAlert()
        return isLocationEnabled
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
            .setPositiveButton("Location Settings") { paramDialogInterface, paramInt ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progress_bar.visibility = View.VISIBLE

        viewModel.vendors.observe(this, Observer { vendors ->

            if (vendors != null) {
                mAdapter = VendorPrevAdapter(vendors, context) { vendor: Vendor ->
                    val intent = Intent(context, VendorDetailsActivity::class.java)
                    intent.putExtra("vendor", vendor)
                    startActivity(intent)
                }
                rootView.rv_vendor_prev.adapter = mAdapter

                if (vendors.size > 0) {
                    tv_no_vendors.visibility = View.INVISIBLE

                } else {
                    tv_no_vendors.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    context,
                    getString(com.example.android.pilgrim.R.string.error),
                    Toast.LENGTH_SHORT
                ).show()
            }

            progress_bar.visibility = View.INVISIBLE

        })
    }


    fun getVendors() {
        Log.i(TAG, "Lat $lat , long $lng")
        viewModel.getVendors(
            "Token $token",
            FindNearestVendorsRequest(
                lng.toString(),
                lat.toString(),
                filter.toString(),
                100f.toString()
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(com.example.android.pilgrim.R.menu.home, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // R.id.action_search -> true
            com.example.android.pilgrim.R.id.action_filter -> {
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


}
