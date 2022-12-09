package com.example.qrcodescanner

import com.example.qrcodescanner.models.BarcodeFeatures

interface FeatureClickHandler {
    fun click(barcodefeature: BarcodeFeatures)
}