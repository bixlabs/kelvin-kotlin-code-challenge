package com.safyah.realtimemap.utils

import android.content.Context
import android.provider.Settings
import com.safyah.realtimemap.models.LocationUpdate
import org.json.JSONObject

object Utils {
    fun uniqueDeviceId(context: Context) : String {
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        return androidId
    }

    fun locationUpdateFromJSON(json: JSONObject) : LocationUpdate {
        val latitude = json.getDouble("latitude")
        val longitude = json.getDouble("longitude")
        val uid = json.getString("uid")

        val locationUpdate = LocationUpdate(latitude, longitude, uid)
        return locationUpdate
    }
}