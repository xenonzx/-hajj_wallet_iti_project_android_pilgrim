package com.example.android.pilgrim.utils

/**
 * Created by Toka on 2019-06-14.
 */
public class LocationHelper() {
/*
    // flag for GPS Status
    private val isGPSEnabled = false
    // flag for network status
    private val isNetworkEnabled = false
    private lateinit var locationManager: LocationManager

    private val location: Location? = null
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    fun getMyLocation(context: Context) {
        locationManager=context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)

        var l: Location? = null
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            /*ActivityCompat.requestPermissions(
                context, String [] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
                LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION
            );*/
        } else {
            for (i in providers.indices) {
                l = locationManager.getLastKnownLocation(providers[i])
                if (l != null)
                    break
            }
            if (l != null) {
                latitude = l.latitude
                longitude = l.longitude
            }
        }
    }*/
}