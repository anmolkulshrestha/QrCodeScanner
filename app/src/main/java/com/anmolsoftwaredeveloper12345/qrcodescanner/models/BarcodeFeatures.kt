package com.anmolsoftwaredeveloper12345.qrcodescanner.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BarcodeFeatures(var featuredesc:String,var resourcefile:Int):Parcelable {
}