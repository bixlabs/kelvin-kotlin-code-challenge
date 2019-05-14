package com.safyah.realtimemap.interactors

import com.safyah.realtimemap.models.LocationUpdate
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class RemoteLocationInteractorTest {

    lateinit var remoteLocationInteractor: RemoteLocationInteractor

    @Before
    fun setUp() {
        remoteLocationInteractor = spyk(RemoteLocationInteractor())
        every { remoteLocationInteractor.updates() } returns Flowable.just(
            LocationUpdate(13.01102, -61.1302, ""),
            LocationUpdate(14.01102, -61.1302, ""))

    }

    @Test
    fun updates() {
        every { remoteLocationInteractor.updates() } returns Flowable.just(LocationUpdate(13.01102, -61.1302, ""))

        remoteLocationInteractor.updates().test()
            .assertResult(
                LocationUpdate(13.01102, -61.1302, ""))
    }



    @Test
    fun updateLocation() {

        every { remoteLocationInteractor.updateLocation(any()) } returns Flowable.just(LocationUpdate(13.01102, -61.1302, ""))

        remoteLocationInteractor.updates().test()
            .assertResult(
                LocationUpdate(13.01102, -61.1302, ""),
                LocationUpdate(14.01102, -61.1302, ""))

    }
}