package com.kaplaukhd.weather.utils

import android.Manifest
import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

object RequestPermission {
     fun checkPermission(context: Context){
        val permissions = mutableListOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        Dexter.withContext(context)
            .withPermissions(permissions)
            .withListener( object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }
            }).check()
    }
}

object Utils{
        const val API_KEY = "YOUR_API_KEY"
        const val UNIT = "metric"
        const val LANG = "ru"
}