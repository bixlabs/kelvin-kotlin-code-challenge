package com.safyah.realtimemap.interactors

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapInteractor(private val map: SupportMapFragment) {
    open fun initialize(onMapReady: OnMapReadyCallback) {
        map.getMapAsync(onMapReady)
    }
}