package com.example.qrcodescanner.utils

import android.content.*

import android.graphics.Bitmap
import android.net.Uri

import android.os.Build
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast

import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.example.qrcodescanner.models.BarcodeTile
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.net.URLConnection


inline fun <T> sdk29AndUp(onSdk29:()->T):T?{
    return  if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
        onSdk29()
    }else {null}
}

inline fun <T> sdk30AndUp(onSdk30:()->T):T?{
    return  if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
        onSdk30()
    }else {null}
}
class FeaturesOptions {


    fun call(tel:String,context: Context){
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$tel")
            startActivity(context,intent,null)

        }

    fun sendSms(smsTel:String,message:String,context: Context){
           val uri = Uri.parse("smsto:$smsTel")
           val intent = Intent(Intent.ACTION_SENDTO, uri)
           intent.putExtra("sms_body", "$message")
           startActivity(context,intent,null)
       }
    fun addToContacts(phoneNo:String,name:String,context: Context){

        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {

            type = ContactsContract.RawContacts.CONTENT_TYPE
        }

        intent.apply {

            putExtra(ContactsContract.Intents.Insert.EMAIL, "")

            putExtra(
                ContactsContract.Intents.Insert.EMAIL_TYPE,
                ContactsContract.CommonDataKinds.Email.TYPE_WORK
            )

            putExtra(ContactsContract.Intents.Insert.PHONE, phoneNo)

            putExtra(
                ContactsContract.Intents.Insert.PHONE_TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK
            )
        }
        startActivity(context,intent,null)
    }



    fun composeEmail(addresses: String, subject: String, message:String,context: Context) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
          putExtra(Intent.EXTRA_TEXT,message)
        }

            startActivity(context,intent,null)

    }



    fun openOnWeb(url:String,context: Context){
      var newurl=""
        if(!url.contains("https") && !url.contains("instagram") && !url.contains("twitter")
            && !url.contains("paypal")){

            newurl="https://"+url
        }else{
            newurl=url
        }
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(newurl)
        startActivity(context,i,null)
    }

    fun  openWhatsApp(phonenumber:String,context: Context){
       try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("http://api.whatsapp.com/send?phone=$phonenumber")
            startActivity(context,intent,null)
        } catch (e:java.lang.Exception) {
            Toast.makeText(
                context,
                "Whats app not installed on your device",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    fun  openInstagram(username:String,context: Context){
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("http://instagram.com/$username")
            startActivity(context,intent,null)
        } catch (e:java.lang.Exception) {
            Toast.makeText(
                context,
                "Instagram app not installed on your device",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun Copy(barcodeTileList:MutableList<BarcodeTile>,context: Context){

        var text:String=""

        for (i in barcodeTileList){
            text=text+"${i.header.toString()} ${i.value.toString()}"+"\n"
        }


        val clipboard: ClipboardManager? = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("copied content", text)
        clipboard!!.setPrimaryClip(clip)
        Toast.makeText(context,"Copied to clipboard",Toast.LENGTH_SHORT).show()

    }
   fun connecttowifi(context:Context){
       var intent=Intent(Settings.ACTION_WIFI_SETTINGS)
       startActivity(context,intent,null)
   }


    suspend fun savePhotoToGalley(context: Context,displayName: String, bmp: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            val imageCollection = sdk29AndUp {
                Log.d("eer", "eer1")
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName")
                Log.d("eer", "eer2")

                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.WIDTH, bmp.width)
                put(MediaStore.Images.Media.HEIGHT, bmp.height)

            }
            try {

                context.contentResolver.insert(imageCollection, contentValues)?.also { uri ->

                    context.contentResolver.openOutputStream(uri).use { outputStream ->

                        if(!bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {

                            throw IOException("Couldn't save bitmap")
                        }
                    }
                } ?: throw IOException("Couldn't create MediaStore entry")
                true
            } catch(e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun savePhotoToInternalStorage(filename: String, bmp: Bitmap,context: Context):Unit{
        return withContext(Dispatchers.IO) {

            try {
               context.openFileOutput("$filename", Context.MODE_PRIVATE).use { stream ->
                    if(!bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {

                        throw IOException("Couldn't save bitmap.")
                    }

                }
                 var fileuri= FileProvider.getUriForFile(context, "kadkl.fileprovider", File(context.filesDir,"$filename"));

                val intentShareFile = Intent(Intent.ACTION_SEND)
                intentShareFile.type = URLConnection.guessContentTypeFromName("$filename")
                intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION )
                intentShareFile.putExtra(
                    Intent.EXTRA_STREAM,
                   fileuri
                )

                startActivity(context,Intent.createChooser(intentShareFile, "Share File"),null)
                true
            } catch(e: IOException) {

                e.printStackTrace()

                false
            }


        }
    }

    fun sendemail(context: Context){
        try {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:"+"softwarevission12345@gmail.com") // only email apps should handle this
            //  intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("example.yahoo.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "App feedback")
            startActivity(context,intent,null)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context,"There is No email app in your device",Toast.LENGTH_SHORT)
        }
    }
fun sharedPreferences(context: Context):Boolean{
    context.getSharedPreferences("SAVE_HISTORY",
        Context.MODE_PRIVATE).let { sharedPreferences ->
        if(sharedPreferences.contains("isSaveHistory")){
         return sharedPreferences.getBoolean("isSaveHistory",true)

        }else{
            sharedPreferences.edit().putBoolean("isSaveHistory",true)
           return true



        }
    }
}

}