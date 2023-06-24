package com.anmolsoftwaredeveloper12345.qrcodescanner.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BarcodeTile(var header:String,var value:String):Parcelable {
}