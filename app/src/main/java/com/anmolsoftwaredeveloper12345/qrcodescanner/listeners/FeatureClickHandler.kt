package com.anmolsoftwaredeveloper12345.qrcodescanner.listeners

import com.anmolsoftwaredeveloper12345.qrcodescanner.models.BarcodeFeatures

interface FeatureClickHandler {
    fun click(barcodefeature: BarcodeFeatures)
}