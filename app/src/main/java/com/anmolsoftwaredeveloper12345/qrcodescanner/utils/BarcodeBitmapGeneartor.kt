package com.anmolsoftwaredeveloper12345.qrcodescanner.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

class BarcodeBitmapGeneartor {
    companion object{
        @Throws(WriterException::class)
         fun createBarcodeBitmap(data: String, width: Int, height: Int): Bitmap? {
            val writer = MultiFormatWriter()
            val finalData: String = Uri.encode(data)


            val bm: BitMatrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, 200, 200)
            val width = bm.width
            val height = bm.height
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (bm[x, y]) Color.BLACK else Color.WHITE
                }
            }
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                setPixels(pixels, 0, width, 0, 0, width, height)
            }



        }

    }
}