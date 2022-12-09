package com.example.qrcodescanner.models

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BarcodeFeatures(var featuredesc:String,var resourcefile:Int):Parcelable {
}