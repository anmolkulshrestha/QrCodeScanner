package com.example.qrcodescanner.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.media.Image
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.qrcodescanner.lumalistener
import com.example.qrcodescanner.models.*
import com.example.qrcodescanner.utils.BarcodeBitmapGeneartor.Companion.createBarcodeBitmap
import com.google.android.libraries.barhopper.RecognitionOptions.CODE_128
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.Barcode.BarcodeFormat
import com.google.mlkit.vision.common.InputImage
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix



import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.*



class LuminosityAnalyzer(var listener:lumalistener,
                         var context:Context,
                         var previewViewWidth:Float,
                         var previewViewHeight:Float) : ImageAnalysis.Analyzer {

    private var isScanning: Boolean = false

    @SuppressLint("UnsafeOptInUsageError")
        override fun analyze(image: ImageProxy) {

            val mediaImage = image.image

            if (mediaImage != null && !isScanning) {
                val inputImage = InputImage
                    .fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
                if(inputImage==null){}
                val options = BarcodeScannerOptions
                    .Builder()
                    .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build()
                val scanner = BarcodeScanning
                    .getClient(options)

                isScanning = true
                val result = scanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->

                        if(barcodes.isNotEmpty()){
                         with(barcodes.first()) {
                             val valueType = this.valueType
                             Log.d("typeaq", this.valueType.toString())

                             when (valueType) {
                                 Barcode.TYPE_WIFI-> {
                                     val ssid = this.wifi!!.ssid
                                     val password = this.wifi!!.password
                                     val type = this.wifi!!.encryptionType
                                     Log.d("typepls", type.toString())



                                     val scannedItem=CustomBarcode("TYPE_WIFI")
                                     scannedItem.wifiNetworkSsid=ssid?:""
                                     scannedItem.wifiNetworkEncryptionType=type.toString()?:""
                                     scannedItem.wifiNetworkPassword=password?:""
                                     scannedItem.ConnectToWifi=true
                                     scannedItem.Share=true
                                     scannedItem.Copy=true
                                     scannedItem.barcodeFormattedText="WIFI:T:$type;S:$ssid;P:$password;;"
                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()

                                 }
                                 Barcode.TYPE_URL -> {
                                     val title = this.url!!.title
                                     val url = this.url!!.url

                                     val scannedItem=CustomBarcode("TYPE_URL")
                                     scannedItem.titleOfWebsite=title?:""
                                     scannedItem.urlOfWebsite=url?:""

                                     scannedItem.barcodeFormattedText="$url"
                                     scannedItem.OpenOnWeb=true
                                     scannedItem.Share=true
                                     scannedItem.Copy=true


                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()

                                 }
                                 Barcode.TYPE_EMAIL->{
                                     val email=this.email!!.address
                                     val subject=this.email!!.subject
                                     val message=this.email!!.body.toString()


                                     val scannedItem=CustomBarcode("TYPE_EMAIL")
                                     scannedItem.email=email?:""
                                     scannedItem.contentOfEmail=message?:""
                                     scannedItem.subjectOfEmail=subject?:""
                                     scannedItem.barcodeFormattedText="mailto:$email?subject=$subject&body=$message"
                                      scannedItem.SendEmail=true
                                     scannedItem.Copy=true
                                     scannedItem.Share=true
                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()

                                 }
                                 Barcode.TYPE_PHONE->{
                                     val phoneno=this.phone!!.number

                                     val scannedItem=CustomBarcode("TYPE_PHONE")
                                     scannedItem.telephoneNumber=phoneno?:""

                                     scannedItem.Copy=true
                                     scannedItem.Share=true
                                     scannedItem.AddToContacts=true
                                     scannedItem.Call=true

                                     scannedItem.barcodeFormattedText="tel:$phoneno"
                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()

                                 }
                                 Barcode.TYPE_SMS->{
                                     val message=this.sms!!.message
                                     val phonenumber=this.sms!!.phoneNumber

                                     val scannedItem=CustomBarcode("TYPE_SMS")
                                     scannedItem.messageOfSms=message?:""
                                     scannedItem.phoneNumberOfSms=phonenumber?:""

                                     scannedItem.Copy=true
                                     scannedItem.Share=true
                                     scannedItem.AddToContacts=true
                                     scannedItem.Call=true

                                     scannedItem.barcodeFormattedText="smsto:+1$phonenumber:$message"
                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()

                                 }
                                 Barcode.TYPE_CONTACT_INFO->{
                                     val name=this.contactInfo!!.name
                                     val organization=this.contactInfo!!.organization
                                     val email=this.contactInfo!!.emails
                                     val addresses=this.contactInfo!!.addresses
                                     val phone=this.contactInfo!!.phones

                                     val scannedItem=CustomBarcode("TYPE_CONTACT_INFO")
                                     scannedItem.email=email.first().toString()?:""
                                     scannedItem.organization=organization.toString()?:""
                                     scannedItem.address=addresses.toString()?:""
                                     scannedItem.name=name.toString()?:""
                                     scannedItem.telephoneNumber=phone.toString()?:""

                                     scannedItem.barcodeFormattedText="MECARD:N:$name;ADR:$addresses;TEL:$phone;EMAIL:$email;;"
                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()


                                 }
                                 Barcode.TYPE_CALENDAR_EVENT->{

                                     val start=this.calendarEvent!!.start!!.rawValue
                                     val end=this.calendarEvent!!.end!!.rawValue
                                     val title=this.calendarEvent!!.description
                                     val organizer=this.calendarEvent!!.organizer
                                     val venue=this.calendarEvent!!.location
                                     val scannedItem=CustomBarcode("TYPE_CALENDAR_EVENT")
                                     scannedItem.venueOfEvent=venue.toString()
                                     scannedItem.organizerOfEvent=organizer.toString()
                                     scannedItem.descriptionOfEvent=title.toString()
                                     scannedItem.startTimeEvent=start.toString()
                                     scannedItem.endTimeEvent=end.toString()

                                     scannedItem.barcodeFormattedText= "BEGIN:VEVENT\n" +
                                             "DTSTART:$start\n" +
                                             "DTEND:$start\n" +
                                             "DESCRIPTION:$title\n"+
                                             "LOCATION:$venue\n"+
                                             "END:VEVENT"
                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()



                                  }


                                 Barcode.TYPE_DRIVER_LICENSE->{
                                     val driverAddressCity=this.driverLicense!!.addressCity
                                     val driverAddressState=this.driverLicense!!.addressState
                                     val driverAddressStreet=this.driverLicense!!.addressStreet
                                     val driverBirthDate=this.driverLicense!!.birthDate
                                     val driverExpiryDate=this.driverLicense!!.expiryDate
                                     val driverFirstName=this.driverLicense!!.firstName
                                     val driverMiddleName=this.driverLicense!!.middleName
                                     val driverGender=this.driverLicense!!.gender
                                     val driverIssueDate=this.driverLicense!!.issueDate
                                     val driverLicenseName=this.driverLicense!!.licenseNumber
                                     val scannedItem=CustomBarcode("TYPE_DRIVER_LICENSE")
                                     scannedItem.driverGender=driverGender.toString()
                                     scannedItem.driverAddressCity=driverAddressCity.toString()
                                     scannedItem.driverAddressState=driverAddressState.toString()
                                     scannedItem.driverFirstName=driverFirstName.toString()
                                     scannedItem.driverAddressStreet=driverAddressStreet.toString()
                                     scannedItem.driverIssueDate=driverIssueDate.toString()
                                     scannedItem.driverLicenseName=driverLicenseName.toString()
                                     scannedItem.driverExpiryDate=driverExpiryDate.toString()
                                     scannedItem.driverBirthDate=driverBirthDate.toString()

                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()



                                 }
                                 else->{
                                     val content=this.rawValue.toString()

                                     val scannedItem=CustomBarcode("TYPE_TEXT")
                                     scannedItem.textItem=content?:""

                                     scannedItem.Copy=true
                                     scannedItem.Share=true
                                     scannedItem.OpenOnWeb=true
//                                     val barcodeBitmap=BarcodeBitmapGeneartor.createBarcodeBitmap("$content",200,200)
//                                    scannedItem.bitmap=barcodeBitmap!!
                                     scannedItem.barcodeFormattedText="$content"
                                     Log.d("textraw",this.rawValue.toString())
                                     listener.sendScannedBarcodeItem(scannedItem)
                                     scanner.close()
                                 }
                             }


                     }
                     }else{

                     }
                        isScanning = false
                        image.close()
                        mediaImage!!.close()


                    }
                    .addOnFailureListener {
                        Toast.makeText(context,"Error Occured While Scanning",Toast.LENGTH_SHORT).show()
                        isScanning = false
                        image.close()
                        mediaImage!!.close()
                    }

            }


        }
    }

