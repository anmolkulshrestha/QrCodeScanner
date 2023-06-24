package com.anmolsoftwaredeveloper12345.qrcodescanner.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateBarcodeOption(var createBarcodeOptionType:String,var createBarcodeOptionDesc:String,var resourcefile:Int):Parcelable {

}