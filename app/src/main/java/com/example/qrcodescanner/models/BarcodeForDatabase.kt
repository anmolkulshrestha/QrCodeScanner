package com.example.qrcodescanner.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName ="barcode_table")
data class BarcodeForDatabase(
    var iconId:Int,
    @Embedded
    var customBarcode: CustomBarcode,
                              var barcodeType:String,
                              var date:Long,




                              var isScanned:Boolean,

                              var isFavourite:Boolean,
                              var nameOfBarcode:String,

   ):Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    var profession:String=""


}