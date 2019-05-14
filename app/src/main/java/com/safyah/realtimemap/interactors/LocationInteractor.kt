package com.safyah.realtimemap.interactors

import com.safyah.realtimemap.models.LocationUpdate
import io.reactivex.Flowable

interface LocationInteractor {
    fun updates() : Flowable<LocationUpdate>
}
