package com.anmolsoftwaredeveloper12345.qrcodescanner.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomBarcode(var type:String):Parcelable{
   var barcodeFormattedText:String?=null
   var email:String=""
   var subjectOfEmail:String=""
   var contentOfEmail:String=""
   var phoneNumberOfSms:String=""
   var messageOfSms:String=""
   var telephoneNumber:String=""
   var textItem:String=""
   var titleOfWebsite=""
   var urlOfWebsite=""
   var wifiNetworkSsid=""
   var wifiNetworkEncryptionType=""
   var wifiNetworkPassword:String=""
   var organization=""
   var address=""
   var name=""
   var driverAddressCity= ""
   var driverAddressState=""
   var driverAddressStreet=""
   var driverBirthDate=""
   var driverExpiryDate=""
   var driverFirstName=""
   var driverMiddleName=""
   var driverGender=""
   var driverIssueDate=""
   var driverLicenseName=""
   var startTimeEvent=""
   var endTimeEvent=""
   var venueOfEvent=""
   var descriptionOfEvent=""
   var organizerOfEvent=""
   var isSaved:Boolean=false
   var SendEmail:Boolean=false
   var AddToContacts:Boolean=false
   var Copy:Boolean=false
   var Share:Boolean=false
   var Call:Boolean=false
   var OpenOnWeb:Boolean=false
   var SendSMS:Boolean=false
   var ConnectToWifi:Boolean=false



}