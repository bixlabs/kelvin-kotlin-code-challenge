package com.safyah.realtimemap.models

import android.util.Log
import com.google.gson.Gson
import com.safyah.realtimemap.utils.Constants
import java.net.URISyntaxException

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

var eventInstance: EventServiceImpl? = null

class EventServiceImpl private constructor() : EventService {

    private val onConnect = Emitter.Listener { args ->
        val gson = Gson()
        Log.i(TAG, "call: onConnect")
        if (mEventListener != null) mEventListener!!.onConnect(*args)
    }

    private val onDisconnect = Emitter.Listener { args ->
        Log.i(TAG, "call: onDisconnect")
        if (mEventListener != null) mEventListener!!.onDisconnect(*args)
    }

    private val onConnectError = Emitter.Listener { args ->
        Log.i(TAG, "call: onConnectError: $args")
        if (mEventListener != null) mEventListener!!.onConnectError(*args)
    }

    private val onLocationUpdate = Emitter.Listener { args ->
        Log.i(TAG, "call: onLocationUpdate")
        if (mEventListener != null) mEventListener!!.onLocationUpdate(*args)
    }

    @Throws(URISyntaxException::class)
    override fun connect() {
        socket = IO.socket(SOCKET_URL)

        socket!!.on(EVENT_CONNECT, onConnect)
        socket!!.on(EVENT_DISCONNECT, onDisconnect)
        socket!!.on(EVENT_CONNECT_ERROR, onConnectError)
        socket!!.on(EVENT_CONNECT_TIMEOUT, onConnectError)
        socket!!.on(EVENT_LOCATION, onLocationUpdate)

        socket!!.connect()
    }

    override fun disconnect() {
        if (socket != null) socket!!.disconnect()
    }

    override fun updateLocation(location: LocationUpdate): Flowable<LocationUpdate> {
        val gson = Gson()
        return Flowable.create({ emitter ->
            socket!!.emit(EVENT_LOCATION, gson.toJson(location))
            emitter.onNext(location)
        }, BackpressureStrategy.BUFFER)
    }

    override fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }

    companion object {

        private val TAG = EventServiceImpl::class.java.simpleName
        private val SOCKET_URL =  Constants.SERVER_URL
        private val EVENT_CONNECT = Socket.EVENT_CONNECT
        private val EVENT_DISCONNECT = Socket.EVENT_DISCONNECT
        private val EVENT_CONNECT_ERROR = Socket.EVENT_CONNECT_ERROR
        private val EVENT_CONNECT_TIMEOUT = Socket.EVENT_CONNECT_TIMEOUT
        private val EVENT_LOCATION = "location"
        private var mEventListener: EventListener? = null
        private var socket: Socket? = null

        val instance: EventService
            get() {
                if(eventInstance == null) {
                    eventInstance = EventServiceImpl()
                }
                return eventInstance!!
            }
    }
}
