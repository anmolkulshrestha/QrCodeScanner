package com.anmolsoftwaredeveloper12345.qrcodescanner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.BarcodeForDatabase

@Database(entities = [BarcodeForDatabase::class], version = 1, exportSchema = true)
abstract class BarcodeDatabase :RoomDatabase(){

    abstract fun barcodeDao():BarcodeDao

    companion object{
        @Volatile
        private var INSTANCE:BarcodeDatabase?=null

        fun getDatabase(context: Context):BarcodeDatabase{
            var temInstance= INSTANCE
            if(temInstance!=null){
                return temInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    BarcodeDatabase::class.java,
                    "barcode_database"
                ).build()
                INSTANCE=
                    return instance
            }
        }
    }

}

