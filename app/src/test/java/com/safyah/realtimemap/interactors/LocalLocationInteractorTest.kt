package com.safyah.realtimemap.interactors

import android.content.Context
import com.safyah.realtimemap.models.LocationUpdate
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class LocalLocationInteractorTest {

    private var context: Context = mockk()
    private lateinit var localLocationInteractor: LocalLocationInteractor

    @Before
    fun setUp() {
        localLocationInteractor = spyk(LocalLocationInteractor(context))
    }

    @Test
    fun updates() {
        every { localLocationInteractor.updates() } returns Flowable.just(LocationUpdate(13.01102, -61.1302, ""))

        assertEquals(LocationUpdate(13.01102, -61.1302, ""), localLocationInteractor.updates().blockingFirst())

    }
}