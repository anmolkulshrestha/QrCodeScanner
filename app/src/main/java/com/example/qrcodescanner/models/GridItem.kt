package com.example.qrcodescanner.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GridItem(var bitmap: Bitmap,var text:String):Parcelable{


}
