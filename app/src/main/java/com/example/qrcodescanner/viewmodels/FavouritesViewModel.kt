package com.example.qrcodescanner.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.qrcodescanner.database.BarcodeDao
import com.example.qrcodescanner.database.BarcodeDatabase
import com.example.qrcodescanner.database.BarcodeRepository
import com.example.qrcodescanner.models.BarcodeForDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouritesViewModel(application: Application): AndroidViewModel(application) {


    var barcodeDao: BarcodeDao = BarcodeDatabase.getDatabase(application).barcodeDao()
    var barcodeRepository: BarcodeRepository = BarcodeRepository(barcodeDao)
    var favouritesBarcodesList: LiveData<List<BarcodeForDatabase>> =
        barcodeRepository.readAllFavouriteBarcodes.asLiveData()

    fun updateIsFavourite(barcodeForDatabase: BarcodeForDatabase) {
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