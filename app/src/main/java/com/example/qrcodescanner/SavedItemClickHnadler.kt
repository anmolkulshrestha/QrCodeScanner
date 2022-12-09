package com.example.qrcodescanner

import com.example.qrcodescanner.models.BarcodeForDatabase
import com.example.qrcodescanner.models.CustomBarcode

interface SavedItemClickHnadler {

    fun handleclickisfavourite(barcode:BarcodeForDatabase)

    fun handleitemclick(customBarcode: CustomBarcode)

    fun deleteclick(barcode: BarcodeForDatabase)
}