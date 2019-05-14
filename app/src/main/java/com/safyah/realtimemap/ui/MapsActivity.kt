package com.safyah.realtimemap.ui

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.safyah.realtimemap.R
import com.safyah.realtimemap.interactors.LocalLocationInteractor
import com.safyah.realtimemap.interactors.MapInteractor
import com.safyah.realtimemap.interactors.RemoteLocationInteractor
import com.safyah.realtimemap.models.LocationUpdate
import com.safyah.realtimemap.mvp.presenters.MapContract
import com.safyah.realtimemap.mvp.presenters.MapPresenter
import com.safyah.realtimemap.utils.Utils
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import android.Manifest.permission
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener


class MapsActivity : AppCompatActivity(), MapContract.View {

    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var presenter : MapPresenter
    private var myMarker : Marker? = null

    fun getPermissions() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    Toast.makeText(this@MapsActivity, "Permission granted", Toast.LENGTH_LONG).show()
                    presenter.initializeMap()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    finish()
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest();
                }
            }).check()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        presenter = createPresenter()
        getPermissions()
    }

    override fun mapReady(googleMap: GoogleMap) {
        mMap = googleMap
        presenter.getLocationUpdates()
    }

    override fun createPresenter(): MapPresenter {
        return MapPresenter(this,
            MapInteractor(mapFragment),
            LocalLocationInteractor(this),
            RemoteLocationInteractor()
        )
    }

    override fun locationUpdated(location: LocationUpdate) {
        val position = LatLng(location.latitude, location.longitude)
        if(isLocalUser(location)) {
            if (myMarker == null)
                myMarker = mMap.addMarker(MarkerOptions().position(position).title(location.uid))
            else
                myMarker?.position = position
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        }
        else {
            mMap.addMarker(MarkerOptions().position(position).title(location.uid))
        }

    }

    fun isLocalUser(location: LocationUpdate) : Boolean
            = Utils.uniqueDeviceId(this) == location.uid


    override fun onResume() {
        super.onResume()
        android.util.Log.d("MapsActivity", "On resume")
    }

    override fun onPause() {
        super.onPause()
        presenter.cleanup()

        android.util.Log.d("MapsActivity", "Pausing updates")
    }
}
