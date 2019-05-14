package com.safyah.realtimemap.interactors

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import io.mockk.*
import org.junit.Before
import org.junit.Test

class MapInteractorTest {

    private lateinit var mapFragment : SupportMapFragment
    private lateinit var mapReadyCallback: OnMapReadyCallback
    private lateinit var googleMap: GoogleMap
    private lateinit var mapInteractor : MapInteractor

    @Before
    fun setUp() {
        mapFragment = mockk()
        mapReadyCallback = mockk()
        googleMap = mockk()
        mapInteractor = MapInteractor(mapFragment)
    }

    @Test
    fun initialize() {

        val slot = slot<OnMapReadyCallback>()

        every {
            mapReadyCallback.onMapReady(any())
        } just runs

        every {
            mapFragment.getMapAsync(capture(slot))
        }.answers {
            slot.captured.onMapReady(googleMap)
        }

        mapInteractor.initialize(mapReadyCallback)

        verify {
            mapReadyCallback.onMapReady(any())
        }
    }
}