package com.example.qrcodescanner

import com.example.qrcodescanner.models.CreateBarcodeOption

interface CreateOptionClickHandler {
    fun createBarcodeOptionClickHandler(createBarcodeOption:CreateBarcodeOption)
}