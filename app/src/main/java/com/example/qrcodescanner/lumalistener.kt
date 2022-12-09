package com.example.qrcodescanner

import com.example.qrcodescanner.models.CustomBarcode

interface lumalistener {
    fun  sendScannedBarcodeItem(obj: CustomBarcode)
        //Access the objects here to update the view

}