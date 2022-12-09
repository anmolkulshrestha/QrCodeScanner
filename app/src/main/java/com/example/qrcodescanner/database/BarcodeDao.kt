package com.example.qrcodescanner.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.qrcodescanner.models.BarcodeForDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface BarcodeDao {
      @Insert(onConflict = OnConflictStrategy.REPLACE)
      suspend fun insertBarcode(barcodeForDatabase: BarcodeForDatabase)

      @Query("SELECT * FROM barcode_table WHERE  isScanned =1 ORDER BY date DESC ")
      fun getAllScannedBarcodes(): Flow<List<BarcodeForDatabase>>



       @Query("SELECT * FROM barcode_table WHERE  isScanned =0 ORDER BY date DESC ")
       fun getAllCreatedBarcodes():Flow<List<BarcodeForDatabase>>

      @Query("SELECT * FROM barcode_table WHERE isFavourite=1 ORDER BY date DESC")
      fun getFavouriteBarcodes():Flow<List<BarcodeForDatabase>>

      @Delete
      suspend fun deleteBarcode(barcodeForDatabase: BarcodeForDatabase)

   @Update
   suspend fun updateIsfavourite(barcodeForDatabase: BarcodeForDatabase)

     @Query("DELETE FROM barcode_table")
     fun deleteAll()

     @Query("SELECT * FROM barcode_table WHERE barcodeType= :type ORDER BY date DESC")
     fun getBarcodesOfTypes(type:String):Flow<List<BarcodeForDatabase>>


}