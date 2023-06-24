package com.anmolsoftwaredeveloper12345.qrcodescanner.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GridItem(var bitmap: Bitmap,var text:String):Parcelable{


}
