package com.safyah.realtimemap.models

import java.net.URISyntaxException

import io.reactivex.Flowable
interface EventService {

    @Throws(URISyntaxException::class)
    fun connect()

    fun disconnect()

    fun setEventListener(listener: EventListener)

    fun updateLocation(location: LocationUpdate): Flowable<LocationUpdate>
}
