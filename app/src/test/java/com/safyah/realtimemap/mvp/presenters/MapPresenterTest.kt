package com.safyah.realtimemap.mvp.presenters

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.safyah.realtimemap.interactors.LocalLocationInteractor
import com.safyah.realtimemap.interactors.MapInteractor
import com.safyah.realtimemap.interactors.RemoteLocationInteractor
import io.mockk.*
import org.junit.Before
import org.junit.Test


class MapPresenterTest() {

    private lateinit var localInteractor: LocalLocationInteractor
    private lateinit var remoteInteractor: RemoteLocationInteractor
    private lateinit var mapInteractor: MapInteractor
    private lateinit var map : SupportMapFragment
    private lateinit var googleMap: GoogleMap
    lateinit var presenter : MapPresenter
    lateinit var view : MapContract.View

    @Before
    fun setUp() {
        view = mockk()
        map = mockk()
        localInteractor = mockk()
        remoteInteractor = mockk()
        mapInteractor = mockk()
        googleMap = mockk()
        presenter = MapPresenter(view, mapInteractor, localInteractor, remoteInteractor)
    }

    @Test
    fun initializeMap() {

        val slot = slot<OnMapReadyCallback>()
        val slot2 = slot<GoogleMap>()

        every { view.mapReady(any()) } just runs

        every { mapInteractor.initialize(capture(slot)) }
            .answers {
                slot.captured.onMapReady(googleMap)
            }
        presenter.initializeMap()

        verify {
            view.mapReady(any())
        }
    }
}