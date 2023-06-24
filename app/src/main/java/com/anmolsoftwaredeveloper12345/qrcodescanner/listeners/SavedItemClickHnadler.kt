package com.anmolsoftwaredeveloper12345.qrcodescanner.listeners

import com.anmolsoftwaredeveloper12345.qrcodescanner.models.BarcodeForDatabase
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.CustomBarcode

interface SavedItemClickHnadler {

    fun handleclickisfavourite(barcode:BarcodeForDatabase)

    fun handleitemclick(customBarcode: CustomBarcode)

    fun deleteclick(barcode: BarcodeForDatabase)
}