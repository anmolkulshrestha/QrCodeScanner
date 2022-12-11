package com.example.qrcodescanner.listeners

import com.example.qrcodescanner.models.CreateBarcodeOption

interface CreateOptionClickHandler {
    fun createBarcodeOptionClickHandler(createBarcodeOption:CreateBarcodeOption)
}