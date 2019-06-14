package com.example.android.pilgrim.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission

/**
 * Created by Toka on 2019-06-14.
 */
class Permissions {
    companion object {

        fun isStoragePermissionGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    return true
                } else {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        5
                    )
                    return false
                }
            } else {
                //permission is automatically granted on sdk<23 upon installation
                return true
            }
        }
    }
}