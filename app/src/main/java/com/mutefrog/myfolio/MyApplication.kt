package com.mutefrog.myfolio

import android.app.Application
import android.graphics.Bitmap
import com.mutefrog.myfolio.utils.ModelPreferencesManager
import com.zynksoftware.documentscanner.ui.DocumentScanner
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ModelPreferencesManager.with(this)

        val configuration = DocumentScanner.Configuration()
        configuration.imageQuality = 100
        configuration.imageSize = 1000000 // 1 MB
        configuration.imageType = Bitmap.CompressFormat.JPEG
        DocumentScanner.init(this, configuration) // or simply DocumentScanner.init(this)

    }
}