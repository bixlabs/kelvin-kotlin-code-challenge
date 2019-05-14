package com.safyah.realtimemap.mvp.presenters

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.safyah.realtimemap.interactors.LocalLocationInteractor
import com.safyah.realtimemap.interactors.MapInteractor
import com.safyah.realtimemap.interactors.RemoteLocationInteractor
import com.safyah.realtimemap.models.LocationUpdate
import com.safyah.realtimemap.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MapPresenter(private val view: MapContract.View,
                   private val mapInteractor: MapInteractor,
                   private val localLocationInteractor: LocalLocationInteractor,
                   private val remoteLocationInteractor: RemoteLocationInteractor)
    : BasePresenter, MapContract.Presenter {

    private var remoteLocationDisposable : Disposable? = null
    private var localLocationDisposable : Disposable? = null

    override fun initializeMap() {
        mapInteractor.initialize(object: OnMapReadyCallback{
            override fun onMapReady(map: GoogleMap) {
                view.mapReady(map)
            }

        })
    }

    override fun getLocationUpdates() {
        localUpdates()
        remoteUpdates()
    }

    fun localUpdates() {
        val localStream = localLocationInteractor.updates()

        localLocationDisposable = localStream
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext {
                view.locationUpdated(it)
                remoteLocationInteractor.updateLocation(it)
            }
            .subscribe {
                android.util.Log.d("MapsActivity", "Got current location " + it)
            }
    }

    fun remoteUpdates() {
        remoteLocationDisposable = remoteLocationInteractor.updates()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext {
            }
            .subscribe {
                view.locationUpdated(it)
                android.util.Log.d("MapsActivity", "Got remote location " + it)
            }
    }

    override fun cleanup() {
        localLocationDisposable?.dispose()
        remoteLocationDisposable?.dispose()
        localLocationDisposable = null
        remoteLocationDisposable = null
    }
}