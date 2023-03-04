package com.mutefrog.myfolio.view.adapter

import java.io.File

interface ImageAdapterListener {
    fun onSaveButtonClicked(image: File)
}