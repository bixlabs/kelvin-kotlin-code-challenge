package com.safyah.realtimemap.interactors

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.support.v4.content.ContextCompat
import com.safyah.realtimemap.models.LocationUpdate
import io.reactivex.Flowable
import org.reactivestreams.Subscriber
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import com.safyah.realtimemap.utils.Utils
import io.reactivex.BackpressureStrategy
import org.intellij.lang.annotations.Flow

class LocalLocationInteractor(private val context: Context) : LocationInteractor {

    val userId : String by lazy {
        Utils.uniqueDeviceId(context)
    }

    override fun updates() : Flowable<LocationUpdate> {
        val rxLocation = RxLocation(context)

        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000)
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            return rxLocation.location().updates(locationRequest).map {
                LocationUpdate(it.latitude, it.longitude, userId)
            }.toFlowable(BackpressureStrategy.BUFFER)
        }
        else {
            return Flowable.just(LocationUpdate(0.0,0.0, userId)
            )
        }
    }
}