package com.example.qrcodescanner.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.qrcodescanner.database.BarcodeDao
import com.example.qrcodescanner.database.BarcodeDatabase
import com.example.qrcodescanner.database.BarcodeRepository
import com.example.qrcodescanner.models.BarcodeForDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ScannedHistoryViewModel(application: Application): AndroidViewModel(application) {



   var barcodeDao: BarcodeDao=BarcodeDatabase.getDatabase(application).barcodeDao()
    var barcodeRepository: BarcodeRepository=BarcodeRepository(barcodeDao)
   var scannedBarcodesList: LiveData<List<BarcodeForDatabase> > = barcodeRepository.readAllScannedBarcodes.asLiveData()

  fun updateIsFavourite(barcodeForDatabase: BarcodeForDatabase){
      GlobalScope.launch(Dispatchers.IO) {
            barcodeRepository.updateIsFavourite(barcodeForDatabase)
      }
  }
    fun deleteBarcode(barcodeForDatabase: BarcodeForDatabase){
        GlobalScope.launch(Dispatchers.IO) {
            barcodeRepository.deleteBarcode(barcodeForDatabase)
        }
    }









}