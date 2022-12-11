package com.example.qrcodescanner.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.listeners.FeatureClickHandler
import com.example.qrcodescanner.R
import com.example.qrcodescanner.adapter.ScannedBarcodeContentAdapter
import com.example.qrcodescanner.adapter.ScannedBarcodeFeaturesAdapter
import com.example.qrcodescanner.database.BarcodeDatabase

import com.example.qrcodescanner.models.*
import com.example.qrcodescanner.utils.BarcodeBitmapGeneartor
import com.example.qrcodescanner.utils.FeaturesOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ViewScannedBarcodeFragment : Fragment() {
    lateinit var scannedBarcode:CustomBarcode
    lateinit var bitmap:ImageView

    var barcodeTileList= mutableListOf<BarcodeTile>()
    var barcodeFeaturesList= mutableListOf<BarcodeFeatures>()
    lateinit var scannedBarcodeItemContentList:RecyclerView
    lateinit var scannedBarcodeFeaturesList:RecyclerView
    private val args by navArgs<ViewScannedBarcodeFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_view_scanned_barcode, container, false)
        scannedBarcodeItemContentList=view.findViewById(R.id.scannedBarcodeItemContentList)
        scannedBarcodeFeaturesList=view.findViewById(R.id.scannedBarcodeItemFeaturesList)
        bitmap=view.findViewById<ImageView>(R.id.barcodeImage)
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        return view



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scannedBarcode=args.custombarcode

         requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=View.GONE

        bitmap.setImageBitmap(BarcodeBitmapGeneartor.createBarcodeBitmap(scannedBarcode.barcodeFormattedText.toString(),200,200))


       if(scannedBarcode.type==BarcodeTypes.TYPE_WIFI.name){

           var secuityType=scannedBarcode.wifiNetworkEncryptionType?:""


           if(secuityType!="nopass"){

               barcodeTileList.add(BarcodeTile("Password:",scannedBarcode.wifiNetworkPassword))
               barcodeTileList.add(BarcodeTile("Security type:",secuityType))
               barcodeTileList.add(BarcodeTile("Network Name:",scannedBarcode.wifiNetworkSsid))

           }else{
               barcodeTileList.add(BarcodeTile("Security type:",secuityType))
               barcodeTileList.add(BarcodeTile("Network Name:",scannedBarcode.wifiNetworkSsid))
           }



           barcodeFeaturesList.add(BarcodeFeatures("Connect to wifi",R.drawable.ic_baseline_network_wifivector))
           barcodeFeaturesList.add(BarcodeFeatures("Copy",R.drawable.ic_baseline_content_copy_vector))
           barcodeFeaturesList.add(BarcodeFeatures("share",R.drawable.ic_baseline_share_vector))
        if(!scannedBarcode.isSaved && FeaturesOptions().sharedPreferences(requireContext())){
            GlobalScope.launch(Dispatchers.IO) {
                try{scannedBarcode.isSaved=true

                    BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                        BarcodeForDatabase(R.drawable.ic_baseline_network_wifivector,scannedBarcode,BarcodeTypes.TYPE_WIFI.nameofitem,System.currentTimeMillis(),true,false,scannedBarcode.wifiNetworkSsid)
                    )
                }catch (e:Exception){
                    withContext(Dispatchers.Main){


                        Toast.makeText(requireContext(),"Something Went Wrong In saving",Toast.LENGTH_SHORT).show()


                    }
                }

            }
        }


       }





        if(scannedBarcode.type==BarcodeTypes.TYPE_URL.name){
            if(scannedBarcode.titleOfWebsite!=""){
                barcodeTileList.add(BarcodeTile("Title:",scannedBarcode.titleOfWebsite))
            }

            barcodeTileList.add(BarcodeTile("Url:",scannedBarcode.urlOfWebsite))

            barcodeFeaturesList.add(BarcodeFeatures("Open",R.drawable.web_search_4291))
            barcodeFeaturesList.add(BarcodeFeatures("Copy",R.drawable.ic_baseline_content_copy_vector))
            barcodeFeaturesList.add(BarcodeFeatures("share",R.drawable.ic_baseline_share_vector))

       if(!scannedBarcode.isSaved && FeaturesOptions().sharedPreferences(requireContext())){
           GlobalScope.launch(Dispatchers.IO) {
               try{scannedBarcode.isSaved=true
                   BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                       BarcodeForDatabase(R.drawable.web_search_4291,scannedBarcode,BarcodeTypes.TYPE_URL.nameofitem,System.currentTimeMillis(),true,false,scannedBarcode.urlOfWebsite)
                   )
               }catch (e:Exception){
                   withContext(Dispatchers.Main){
                       Toast.makeText(requireContext(),"Something Went Wrong In saving",Toast.LENGTH_SHORT).show()
                   }
               }


           }
       }


        }
       if(scannedBarcode.type==BarcodeTypes.TYPE_EMAIL.name){
            barcodeTileList.add(BarcodeTile("To:",scannedBarcode.email))
            barcodeTileList.add(BarcodeTile("Subject:",scannedBarcode.subjectOfEmail))
            barcodeTileList.add(BarcodeTile("Content:",scannedBarcode.contentOfEmail))

            barcodeFeaturesList.add(BarcodeFeatures("Send Email",R.drawable.ic_baseline_emailvector))
            barcodeFeaturesList.add(BarcodeFeatures("add to contacts",R.drawable.ic_baseline_add_tocontactvector))
            barcodeFeaturesList.add(BarcodeFeatures("Copy",R.drawable.ic_baseline_content_copy_vector))
            barcodeFeaturesList.add(BarcodeFeatures("share",R.drawable.ic_baseline_share_vector))
           if(!scannedBarcode.isSaved && FeaturesOptions().sharedPreferences(requireContext())){
               GlobalScope.launch(Dispatchers.IO) {
                   try{scannedBarcode.isSaved=true
                       BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                           BarcodeForDatabase(R.drawable.ic_baseline_emailvector,scannedBarcode,BarcodeTypes.TYPE_EMAIL.nameofitem,System.currentTimeMillis(),true,false,scannedBarcode.email)
                       )
                   }catch (e:Exception){
                       withContext(Dispatchers.Main){
                           Toast.makeText(requireContext(),"Something Went Wrong In saving",Toast.LENGTH_SHORT).show()
                       }
                   }


               }
           }




        }
       if(scannedBarcode.type==BarcodeTypes.TYPE_PHONE.name) {barcodeTileList.add(BarcodeTile("Tel:",scannedBarcode.telephoneNumber))

            barcodeFeaturesList.add(BarcodeFeatures("Call",R.drawable.ic_baseline_callvector))
            barcodeFeaturesList.add(BarcodeFeatures("add to contacts",R.drawable.ic_baseline_add_tocontactvector))
            barcodeFeaturesList.add(BarcodeFeatures("Copy",R.drawable.ic_baseline_content_copy_vector))
            barcodeFeaturesList.add(BarcodeFeatures("share",R.drawable.ic_baseline_share_vector))
         if(!scannedBarcode.isSaved && FeaturesOptions().sharedPreferences(requireContext())){
    GlobalScope.launch(Dispatchers.IO) {
        try{scannedBarcode.isSaved=true

            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                BarcodeForDatabase(R.drawable.ic_baseline_callvector,scannedBarcode,BarcodeTypes.TYPE_PHONE.nameofitem,System.currentTimeMillis(),true,false,scannedBarcode.telephoneNumber)
            )
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(),"Something Went Wrong In saving",Toast.LENGTH_SHORT).show()
            }
        }


    }
}

        }
        if(scannedBarcode.type==BarcodeTypes.TYPE_SMS.name){
            barcodeTileList.add(BarcodeTile("Tel:",scannedBarcode.phoneNumberOfSms))
            barcodeTileList.add(BarcodeTile("Message:",scannedBarcode.messageOfSms))
            barcodeFeaturesList.add(BarcodeFeatures("Call",R.drawable.ic_baseline_callvector))
            barcodeFeaturesList.add(BarcodeFeatures("add to contacts",R.drawable.ic_baseline_add_tocontactvector))
            barcodeFeaturesList.add(BarcodeFeatures("Open",R.drawable.web_search_4291))
            barcodeFeaturesList.add(BarcodeFeatures("Copy", R.drawable.ic_baseline_content_copy_vector))
            barcodeFeaturesList.add(BarcodeFeatures("share",R.drawable.ic_baseline_share_vector))

            if(!scannedBarcode.isSaved && FeaturesOptions().sharedPreferences(requireContext())){
                GlobalScope.launch(Dispatchers.IO) {
                    try{scannedBarcode.isSaved=true
                        BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                            BarcodeForDatabase(R.drawable.ic_baseline_smsvector,scannedBarcode,BarcodeTypes.TYPE_SMS.nameofitem,System.currentTimeMillis(),true,false,scannedBarcode.phoneNumberOfSms)
                        )
                    }catch (e:Exception){
                        withContext(Dispatchers.Main){
                            Toast.makeText(requireContext(),"Something Went Wrong In saving",Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            }

        }
        if(scannedBarcode.type==BarcodeTypes.TYPE_TEXT.name){
            barcodeTileList.add(BarcodeTile("Text:",scannedBarcode.textItem))

            barcodeFeaturesList.add(BarcodeFeatures("Open",R.drawable.web_search_4291))
            barcodeFeaturesList.add(BarcodeFeatures("Copy",R.drawable.ic_baseline_content_copy_vector))
            barcodeFeaturesList.add(BarcodeFeatures("share",R.drawable.ic_baseline_share_vector))
          if(!scannedBarcode.isSaved && FeaturesOptions().sharedPreferences(requireContext())){

              GlobalScope.launch(Dispatchers.IO) {
                  try{scannedBarcode.isSaved=true
                      BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                          BarcodeForDatabase(R.drawable.ic_baseline_textcontent,scannedBarcode,BarcodeTypes.TYPE_TEXT.nameofitem,System.currentTimeMillis(),true,false,scannedBarcode.textItem)
                      )
                  }catch (e:Exception){
                      withContext(Dispatchers.Main){
                          Toast.makeText(requireContext(),"Something Went Wrong In saving",Toast.LENGTH_SHORT).show()
                      }
                  }


              }
          }

        }
        if(scannedBarcode.type==BarcodeTypes.TYPE_CONTACT_INFO.name){
            barcodeTileList.add(BarcodeTile("Name:",scannedBarcode.name))
            barcodeTileList.add(BarcodeTile("address:",scannedBarcode.address))
            barcodeTileList.add(BarcodeTile("organization:",scannedBarcode.organization))
            barcodeTileList.add(BarcodeTile("email:",scannedBarcode.email))
            barcodeTileList.add(BarcodeTile("Tel:",scannedBarcode.telephoneNumber))

            barcodeFeaturesList.add(BarcodeFeatures("Open",R.drawable.web_search_4291))
            barcodeFeaturesList.add(BarcodeFeatures("Send Email",R.drawable.ic_baseline_emailvector))
            barcodeFeaturesList.add(BarcodeFeatures("Call",R.drawable.ic_baseline_callvector))
            barcodeFeaturesList.add(BarcodeFeatures("add to contacts",R.drawable.ic_baseline_add_tocontactvector))
            barcodeFeaturesList.add(BarcodeFeatures("Copy",R.drawable.ic_baseline_content_copy_vector))
            barcodeFeaturesList.add(BarcodeFeatures("share",R.drawable.ic_baseline_share_vector))
           if(!scannedBarcode.isSaved && FeaturesOptions().sharedPreferences(requireContext())){
               GlobalScope.launch(Dispatchers.IO) {
                   try{scannedBarcode.isSaved=true
                       BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                           BarcodeForDatabase(R.drawable.phone_book_contacts_svgrepo_com,scannedBarcode,BarcodeTypes.TYPE_CONTACT_INFO.nameofitem,System.currentTimeMillis(),true,false,scannedBarcode.name)
                       )
                   }catch (e:Exception){
                       withContext(Dispatchers.Main){
                           Toast.makeText(requireContext(),"Something Went Wrong In saving",Toast.LENGTH_SHORT).show()
                       }
                   }


               }
           }


        }
       if(scannedBarcode.type==BarcodeTypes.TYPE_CALENDAR_EVENT.name){
            var startYear=scannedBarcode.startTimeEvent.subSequence(0,4)
            var startMonth=scannedBarcode.startTimeEvent.subSequence(4,6)
            var startDate=scannedBarcode.startTimeEvent.subSequence(6,8)
            var startTime=scannedBarcode.startTimeEvent.subSequence(9,13)
            var endYear=scannedBarcode.endTimeEvent.subSequence(0,4)
            var endMonth=scannedBarcode.endTimeEvent.subSequence(4,6)
            var endDate=scannedBarcode.endTimeEvent.subSequence(6,8)
            var endTime=scannedBarcode.endTimeEvent.subSequence(9,13)

         if(scannedBarcode.organizerOfEvent!=""){
             barcodeTileList.add(BarcodeTile("Organizer:",scannedBarcode.organizerOfEvent))
         }
            if(scannedBarcode.venueOfEvent!=""){
                barcodeTileList.add(BarcodeTile("Venue Of Event:",scannedBarcode.venueOfEvent))
            }

            barcodeTileList.add(BarcodeTile("Start Time","$startDate-$startMonth-$startYear-$startTime"))
            barcodeTileList.add(BarcodeTile("End Time:","$endDate-$endMonth-$endYear-$endTime"))


            barcodeTileList.add(BarcodeTile("Description:",scannedBarcode.descriptionOfEvent))

            barcodeFeaturesList.add(BarcodeFeatures("Send Email", R.drawable.ic_baseline_emailvector))
            barcodeFeaturesList.add(BarcodeFeatures("Copy",R.drawable.ic_baseline_content_copy_vector))
            barcodeFeaturesList.add(BarcodeFeatures("share",R.drawable.ic_baseline_share_vector))
              if(!scannedBarcode.isSaved && FeaturesOptions().sharedPreferences(requireContext())){
          GlobalScope.launch(Dispatchers.IO) {
        try{scannedBarcode.isSaved=true
            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                BarcodeForDatabase(R.drawable.ic_baseline_calendar_vector,scannedBarcode,BarcodeTypes.TYPE_CALENDAR_EVENT.nameofitem,System.currentTimeMillis(),true,false,scannedBarcode.descriptionOfEvent)
            )
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(),"Something Went Wrong In saving",Toast.LENGTH_SHORT).show()
            }
        }


    }
}


        }


        val adapter=ScannedBarcodeContentAdapter()
        val featuresAdapter=ScannedBarcodeFeaturesAdapter(object : FeatureClickHandler {
            override fun click(barcodefeature: BarcodeFeatures) {
                 val type=barcodefeature.featuredesc
                when(type){
                    "Copy"->{FeaturesOptions().Copy(barcodeTileList,requireContext())}
                    "Call"->{
                        if(scannedBarcode.telephoneNumber!=""){
                            FeaturesOptions()
                                .call(scannedBarcode.telephoneNumber,requireContext())
                        }
                    if(scannedBarcode.phoneNumberOfSms!=""){
                        FeaturesOptions()
                            .call(scannedBarcode.phoneNumberOfSms,requireContext())
                    }
                    }
                    "Connect to wifi"->{

                        FeaturesOptions().connecttowifi(requireContext())
                    }
                    "Open"->{
                        if(scannedBarcode.textItem.contains("whatsapp://")){
                            FeaturesOptions().openWhatsApp(scannedBarcode.textItem.split("phone=")[1],requireContext())
                        }
                      else  if(scannedBarcode.textItem.contains("instagram://")){
                            FeaturesOptions().openInstagram(scannedBarcode.textItem.split("username=")[1],requireContext())
                        }
                      else  if(scannedBarcode.urlOfWebsite!=""){
                            FeaturesOptions().openOnWeb(scannedBarcode.urlOfWebsite,requireContext())
                        }
                        else if(scannedBarcode.messageOfSms!=""){
                            FeaturesOptions().openOnWeb(scannedBarcode.messageOfSms,requireContext())
                        }
                        else if(scannedBarcode.textItem!=""){
                            FeaturesOptions().openOnWeb(scannedBarcode.textItem,requireContext())
                        }
                        else if(scannedBarcode.address!=""){

                        }
                        else if(scannedBarcode.venueOfEvent!=""){
                            FeaturesOptions().openOnWeb(scannedBarcode.venueOfEvent,requireContext())
                        }



                        }
                    "share"->{
                        GlobalScope.launch(Dispatchers.IO) {

                            FeaturesOptions().savePhotoToInternalStorage(System.currentTimeMillis().toString()+".jpg",
                                BarcodeBitmapGeneartor.createBarcodeBitmap(scannedBarcode.barcodeFormattedText.toString(),200,200)!!,

                                requireContext())


                        }
                    }
                    "add to contacts"->{
                        if(scannedBarcode.telephoneNumber!=""){
                            FeaturesOptions()
                                .addToContacts(scannedBarcode.telephoneNumber,"",requireContext())
                        }
                        if(scannedBarcode.phoneNumberOfSms!=""){
                            FeaturesOptions()
                                .addToContacts(scannedBarcode.phoneNumberOfSms,"",requireContext())
                        }
                    }

                }

            }
        })
        var featuresLayoutManager=GridLayoutManager(activity,4)
        var layoutmanager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        scannedBarcodeItemContentList.layoutManager = layoutmanager
        scannedBarcodeFeaturesList.layoutManager=featuresLayoutManager
        scannedBarcodeFeaturesList.adapter=featuresAdapter
        scannedBarcodeItemContentList.adapter=adapter
        adapter.bindlist(barcodeTileList)


        featuresAdapter.bindlist(barcodeFeaturesList)

    }}


