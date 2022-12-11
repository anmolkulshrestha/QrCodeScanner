package com.example.qrcodescanner.models

import com.google.mlkit.vision.barcode.common.Barcode.TYPE_CONTACT_INFO
import com.google.mlkit.vision.barcode.common.Barcode.TYPE_SMS

enum class BarcodeTypes(var nameofitem:String="") {

    TYPE_WIFI("Wi-fi"),
    TYPE_TEXT("Text"),
    TYPE_URL("url"),
    TYPE_EMAIL("email"),
    TYPE_PHONE("phone"),
    TYPE_SMS("sms"),
    TYPE_CONTACT_INFO("contact"),
    TYPE_CALENDAR_EVENT("calendar"),
    TYPE_MYCARD("my-card"),
    TYPE_WHATSAPP("whatsapp"),
    TYPE_INSTAGRAM("instagram"),
    TYPE_TWITTER("twitter"),
    TYPE_PAYPAL("paypal")


}