package com.example.qrcodescanner.listeners

import com.example.qrcodescanner.models.BarcodeFeatures

interface FeatureClickHandler {
    fun click(barcodefeature: BarcodeFeatures)
}