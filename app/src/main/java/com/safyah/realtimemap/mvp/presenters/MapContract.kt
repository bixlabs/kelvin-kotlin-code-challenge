package com.safyah.realtimemap.mvp.presenters

import com.google.android.gms.maps.GoogleMap
import com.safyah.realtimemap.models.LocationUpdate
import com.safyah.realtimemap.mvp.views.BaseView

interface MapContract {
    interface View : BaseView<Presenter> {
        fun mapReady(map: GoogleMap)
        fun locationUpdated(location: LocationUpdate)
    }

    interface Presenter: BasePresenter {
        fun initializeMap()
        fun getLocationUpdates()
        fun cleanup()
    }
}