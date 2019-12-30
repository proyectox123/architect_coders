package com.mho.training.permissions

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener

class PermissionRequester(private val activity: Activity) {

    fun requestAccessCoarseLocation(continuation: (Boolean) -> Unit) {
        Dexter
            .withActivity(activity)
            .withPermission(ACCESS_COARSE_LOCATION)
            .withListener(object : BasePermissionListener() {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    continuation(true)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    continuation(false)
                }
            })
            .check()
    }
}