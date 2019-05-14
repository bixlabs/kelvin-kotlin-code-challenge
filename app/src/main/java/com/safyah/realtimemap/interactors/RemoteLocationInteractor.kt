package com.safyah.realtimemap.interactors

import android.location.Location
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.safyah.realtimemap.models.EventListener
import com.safyah.realtimemap.models.EventServiceImpl
import com.safyah.realtimemap.models.LocationUpdate
import com.safyah.realtimemap.utils.Utils
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow
import org.json.JSONObject

class RemoteLocationInteractor: LocationInteractor {
    private val eventService = EventServiceImpl.instance

    override fun updates(): Flowable<LocationUpdate> {

        return Flowable.create({ emitter ->

            eventService.connect()
            eventService.setEventListener(object: EventListener {
                override fun onConnect(vararg args: Any?) {
                    android.util.Log.d("MapsActivity", "Remote connected ")

                }

                override fun onDisconnect(vararg args: Any?) {
                    android.util.Log.d("MapsActivity", "Remote disconnected " + args)
                }

                override fun onConnectError(vararg args: Any?) {
                }

                override fun onConnectTimeout(vararg args: Any?) {
                }

                override fun onLocationUpdate(vararg args: Any?) {
                    val json = args[0] as JSONObject
                    android.util.Log.d("MapsActivity", "Remote update " + (args[0] is JSONObject))
                    val locationUpdate = Utils.locationUpdateFromJSON(json)
                    emitter.onNext(locationUpdate)                }

            })

        }, BackpressureStrategy.BUFFER)

    }

    fun updateLocation(location: LocationUpdate) : Flowable<LocationUpdate>{
        return eventService.updateLocation(location)
    }

}