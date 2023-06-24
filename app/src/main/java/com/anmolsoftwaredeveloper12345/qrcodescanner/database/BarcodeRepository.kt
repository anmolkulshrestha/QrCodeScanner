package com.anmolsoftwaredeveloper12345.qrcodescanner.database

import com.anmolsoftwaredeveloper12345.qrcodescanner.models.BarcodeForDatabase
import kotlinx.coroutines.flow.Flow
class BarcodeRepository(private val barcodeDao: BarcodeDao) {
    val readAllScannedBarcodes:Flow<List<BarcodeForDatabase>> = barcodeDao.getAllScannedBarcodes()

    val readAllCreatedBarcodes:Flow<List<BarcodeForDatabase>> = barcodeDao.getAllCreatedBarcodes()


    val readAllFavouriteBarcodes:Flow<List<BarcodeForDatabase>> = barcodeDao.getFavouriteBarcodes()

    fun getBarcodeOfType(type:String){
      val getBarcodeOfType:Flow<List<BarcodeForDatabase>> = barcodeDao.getBarcodesOfTypes(type)

    }
    suspend fun updateIsFavourite(barcodeForDatabase: BarcodeForDatabase){
     barcodeDao.updateIsfavourite(barcodeForDatabase)
    }

    suspend fun deleteAllBarcodes(){
       barcodeDao.deleteAll()
   }

    suspend fun deleteBarcode(barcodeForDatabase: BarcodeForDatabase){
        barcodeDao.deleteBarcode(barcodeForDatabase)
    }


    suspend fun insertBarcode(barcodeForDatabase: BarcodeForDatabase){
        barcodeDao.insertBarcode(barcodeForDatabase)

    }



}