package com.example.qrcodescanner.fragments


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.pm.ActivityInfo
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.qrcodescanner.R

import com.example.qrcodescanner.database.BarcodeDatabase
import com.example.qrcodescanner.models.BarcodeForDatabase
import com.example.qrcodescanner.models.BarcodeTypes
import com.example.qrcodescanner.models.CustomBarcode
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_generate_barcode.*
import kotlinx.android.synthetic.main.fragment_generate_barcode.view.*
import kotlinx.android.synthetic.main.fragment_view_scanned_barcode.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class GenerateBarcodeFragment : Fragment() ,AdapterView.OnItemSelectedListener{
    private val args by navArgs<GenerateBarcodeFragmentArgs>()
    lateinit var datePickerDialog:DatePickerDialog
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var email:TextInputLayout
    lateinit var password:TextInputLayout

    lateinit var phoneNumber:TextInputLayout
    lateinit var Url:TextInputLayout
    lateinit var SubjectOfEmail:TextInputLayout
    lateinit var emailBody:TextInputLayout
    lateinit var smsMessage:TextInputLayout
    lateinit var encyptionType:CardView
    lateinit var networkSSID:TextInputLayout
    lateinit var text:TextInputLayout
    lateinit var eventlocation:TextInputLayout
    lateinit var eventorganizer:TextInputLayout
    lateinit var address:TextInputLayout
    lateinit var name:TextInputLayout
    lateinit var starttimelayout:TextInputLayout
    lateinit var profession:TextInputLayout
    lateinit var endtimelayout:TextInputLayout
    lateinit var description:TextInputLayout
    lateinit var titleofevent:TextInputLayout
    lateinit var birthday:TextInputLayout
    lateinit var typeimage:ImageView
  lateinit var securityType:Spinner
 var security:String="WPA/WPA2"
    var pickedDateForBarcodeString:String=""
    var  pickedTimeForBarcodeString:String=""
    var finalStartDateForBarcodeString:String=""
    var  finalStartTimeForBarcodeString:String=""
    var finalEndDateForBarcodeString:String=""
    var  finalEndTimeForBarcodeString:String=""

    lateinit var button:Button
    lateinit var customBarcode: CustomBarcode

    var barcodeString:String=""
    var presentMonth:Int=0
    var presentYear:Int=0
    var presentDate:Int=0
    var presentHour:Int=0
    var presentMinute:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(com.example.qrcodescanner.R.layout.fragment_generate_barcode, container, false)
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        return view
     }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(com.example.qrcodescanner.R.id.bottomNavigationView).visibility=View.GONE

        email=view.findViewById(com.example.qrcodescanner.R.id.email)
        password=view.findViewById(com.example.qrcodescanner.R.id.password)
        SubjectOfEmail=view.findViewById(com.example.qrcodescanner.R.id.Subject)
        phoneNumber=view.findViewById(com.example.qrcodescanner.R.id.phoneNumber)
        emailBody=view.findViewById(com.example.qrcodescanner.R.id.emailBody)
        Url=view.findViewById(com.example.qrcodescanner.R.id.url)
        text=view.findViewById(com.example.qrcodescanner.R.id.text)
        smsMessage=view.findViewById(com.example.qrcodescanner.R.id.smsMessage)
        networkSSID=view.findViewById(com.example.qrcodescanner.R.id.networkSSID)

        address=view.findViewById(com.example.qrcodescanner.R.id.address)
        name=view.findViewById(com.example.qrcodescanner.R.id.name)
        starttimelayout=view.findViewById(com.example.qrcodescanner.R.id.starttime)
        endtimelayout=view.findViewById(com.example.qrcodescanner.R.id.endtime)
        button=view.findViewById(com.example.qrcodescanner.R.id.button)
        description=view.findViewById(com.example.qrcodescanner.R.id.eventdescription)
        birthday=view.findViewById(com.example.qrcodescanner.R.id.birthdate)
        titleofevent=view.findViewById(com.example.qrcodescanner.R.id.titleofevent)
        eventlocation=view.findViewById(com.example.qrcodescanner.R.id.eventlocation)
        eventorganizer=view.findViewById(com.example.qrcodescanner.R.id.eventorganizer)
        profession=view.findViewById(R.id.profession)
        encyptionType=view.findViewById(R.id.encyptionType)
        typeimage=view.findViewById(R.id.typeimage)

        val c: Calendar = Calendar.getInstance()
        presentYear = c.get(Calendar.YEAR)
        presentMonth = c.get(Calendar.MONTH)
        presentDate = c.get(Calendar.DAY_OF_MONTH)
        val cc = Calendar.getInstance()
        presentHour = c[Calendar.HOUR_OF_DAY]
        presentMinute= c[Calendar.MINUTE]
         securityType=view.findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener=this
        val items = listOf("WPA/WPA2", "WEP", "None")
        val adapter = ArrayAdapter(requireContext(),R.layout.list_item,items)


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       spinner.setAdapter(adapter);




        starttimelayout.setEndIconOnClickListener {










            TimePickerDialog(
                requireContext(),
                OnTimeSetListener { view, hourOfDay, minute ->
                    presentHour = hourOfDay
                    presentMinute = minute
                    var formattedTime=String.format("%02d:%02d", presentHour, presentMinute)
                    var formattedDate=String.format("%02d-%02d-%02d", presentYear, presentMonth,presentDate)
                    pickedTimeForBarcodeString=String.format("%02d%02d", presentHour, presentMinute)
                    pickedDateForBarcodeString=String.format("%02d%02d%02d", presentYear, presentMonth,presentDate)
                    finalStartDateForBarcodeString=pickedDateForBarcodeString
                    finalStartTimeForBarcodeString=pickedTimeForBarcodeString
                    starttimelayout.editText!!.setText("$formattedDate  $formattedTime")


                },
                presentHour,
                presentMinute,
                true
            ).show()

            DatePickerDialog(requireContext(),
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    presentYear=year
                    presentMonth=monthOfYear
                    presentDate=dayOfMonth},
                presentYear,
                presentMonth,
                presentDate
            ).show()










        }
        endtimelayout.setEndIconOnClickListener {












            TimePickerDialog(
                requireContext(),
                OnTimeSetListener { view, hourOfDay, minute ->
                    presentHour = hourOfDay
                    presentMinute = minute
                    var formattedTime=String.format("%02d:%02d", presentHour, presentMinute)
                    var formattedDate=String.format("%02d-%02d-%02d", presentYear, presentMonth,presentDate)
                    pickedTimeForBarcodeString=String.format("%02d%02d", presentHour, presentMinute)
                    pickedDateForBarcodeString=String.format("%02d%02d%02d", presentYear, presentMonth,presentDate)
                    finalEndDateForBarcodeString=pickedDateForBarcodeString
                    finalEndTimeForBarcodeString=pickedTimeForBarcodeString
                   endtimelayout.editText!!.setText("$formattedDate  $formattedTime")


                },
                presentHour,
                presentMinute,
                true
            ).show()
            DatePickerDialog(requireContext(),
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    presentYear=year
                    presentMonth=monthOfYear
                    presentDate=dayOfMonth},
                presentYear,
                presentMonth,
                presentDate
            ).show()




        }


            identifyBarcodeAndSetup()
             button.setOnClickListener {
            identifyBarcodeAndSetup()
               }





    }



    fun identifyBarcodeAndSetup() {

        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.createbarcodeoption.createBarcodeOptionDesc.toString()
        var type = args.createbarcodeoption.createBarcodeOptionType.toString()
        when (type) {
            "TYPE_WHATSAPP"->{
                typeimage.setImageResource(R.drawable.icons8_whatsapp)
                phoneNumber.visibility = View.VISIBLE
                if (phoneNumber.editText!!.text.isNotEmpty()) {
                    barcodeString = "whatsapp://send?phone="+"${phoneNumber.editText!!.text}"
                    customBarcode=CustomBarcode("TYPE_WHATSAPP")
                    customBarcode.telephoneNumber=phoneNumber.editText!!.text.toString()

                    GlobalScope.launch(Dispatchers.IO) {
                        try {
                            customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(
                                    R.drawable.icons8_whatsapp,customBarcode,BarcodeTypes.TYPE_WHATSAPP.nameofitem,System.currentTimeMillis(),false,false,phoneNumber.editText!!.text.toString()
                              )
                            )
                        }catch (e:Exception){
                            Log.d("roomsaveerror",e.toString() )
                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )}


            }
            BarcodeTypes.TYPE_PHONE.name -> {
                typeimage.setImageResource(R.drawable.ic_baseline_callvector)
                phoneNumber.visibility = View.VISIBLE
                if (phoneNumber.editText!!.text.isNotEmpty()) {
                    barcodeString = "tel:${phoneNumber.editText!!.text}"
                    customBarcode=CustomBarcode(BarcodeTypes.TYPE_PHONE.name)
                    customBarcode.telephoneNumber=phoneNumber.editText!!.text.toString()

                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )
                    GlobalScope.launch(Dispatchers.IO) {

                        try{ customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.ic_baseline_callvector, customBarcode,BarcodeTypes.TYPE_PHONE.nameofitem,System.currentTimeMillis(),false,false,phoneNumber.editText!!.text.toString() )
                               )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show()

                        }

                    }

                }else{
Toast.makeText(requireContext(),"Empty Tel",Toast.LENGTH_SHORT).show()
                }


            }

            BarcodeTypes.TYPE_EMAIL.name -> {
                typeimage.setImageResource(R.drawable.ic_baseline_emailvector)
                email.visibility = View.VISIBLE
                SubjectOfEmail.visibility = View.VISIBLE
                emailBody.visibility = View.VISIBLE
                if (email.editText!!.text.isNotEmpty()
                        && SubjectOfEmail.editText!!.text.isNotEmpty()
                    && emailBody.editText!!.text.isNotEmpty()) {
                    barcodeString =
                        "mailto:${email.editText!!.text}?subject=${SubjectOfEmail.editText!!.text}&body=${emailBody.editText!!.text}"
                   customBarcode= CustomBarcode(BarcodeTypes.TYPE_EMAIL.name)
                    customBarcode.email=email.editText!!.text.toString()
                    customBarcode.subjectOfEmail=SubjectOfEmail.editText!!.text.toString()
                    customBarcode.contentOfEmail=emailBody.editText!!.text.toString()

                    GlobalScope.launch(Dispatchers.IO) {

                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.ic_baseline_emailvector,customBarcode,BarcodeTypes.TYPE_EMAIL.nameofitem,System.currentTimeMillis(),false,false,email.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){
                            Log.d("roomsaveerror",e.toString() )
                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )
                }else{

                    Toast.makeText(requireContext(),"Empty Fields",Toast.LENGTH_SHORT).show()

                }
            }
            BarcodeTypes.TYPE_URL.name -> {
                typeimage.setImageResource(R.drawable.web_search_4291)
                Url.visibility = View.VISIBLE
                if (Url.editText!!.text.isNotEmpty()) {
                    barcodeString = "${Url.editText!!.text}"
                    if(!barcodeString.contains("https")){
                        barcodeString="https://"+barcodeString
                    }
               customBarcode= CustomBarcode(BarcodeTypes.TYPE_URL.name)
                    customBarcode.urlOfWebsite=barcodeString
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.web_search_4291,customBarcode,BarcodeTypes.TYPE_URL.nameofitem,System.currentTimeMillis(),false,false,barcodeString
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show()

                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )
                }


            }
            "TYPE_TWITTER"-> {
                typeimage.setImageResource(R.drawable.icons8_twittervector)
                Url.visibility = View.VISIBLE
                Url.hint="Username"
                if (Url.editText!!.text.isNotEmpty()) {
                    barcodeString ="twitter://user?screen_name=${Url.editText!!.text}"
                    customBarcode= CustomBarcode(BarcodeTypes.TYPE_URL.name)
                    customBarcode.urlOfWebsite=barcodeString
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.icons8_twittervector,customBarcode,BarcodeTypes.TYPE_TWITTER.nameofitem,System.currentTimeMillis(),false,false,Url.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show()

                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )
                }else{
                    Toast.makeText(requireContext(),"Input Username",Toast.LENGTH_SHORT).show()

                }


            }
            "TYPE_PAYPAL"-> {
                typeimage.setImageResource(R.drawable.icons8_paypalvector)
                Url.visibility = View.VISIBLE
                Url.hint="Username"
                if (Url.editText!!.text.isNotEmpty()) {
                    barcodeString ="https://www.paypal.me/${Url.editText!!.text}"
                    customBarcode= CustomBarcode(BarcodeTypes.TYPE_URL.name)
                    customBarcode.urlOfWebsite=barcodeString
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.icons8_paypalvector,customBarcode,BarcodeTypes.TYPE_PAYPAL.nameofitem,System.currentTimeMillis(),false,false,Url.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show()

                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )
                }else{
                    Toast.makeText(requireContext(),"Input Username",Toast.LENGTH_SHORT).show()

                }


            }
            "TYPE_INSTAGRAM"-> {
                typeimage.setImageResource(R.drawable.icons8_instagram)
                Url.visibility = View.VISIBLE
                Url.hint="Username"
                if (Url.editText!!.text.isNotEmpty()) {
                    barcodeString ="instagram://user?username=${Url.editText!!.text}"
                    customBarcode= CustomBarcode(BarcodeTypes.TYPE_URL.name)
                    customBarcode.urlOfWebsite=barcodeString
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.icons8_instagram,customBarcode,BarcodeTypes.TYPE_INSTAGRAM.nameofitem,System.currentTimeMillis(),false,false,Url.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show()

                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )
                }else{
                    Toast.makeText(requireContext(),"Input Username",Toast.LENGTH_SHORT).show()

                }


            }
            BarcodeTypes.TYPE_WIFI.name -> {
                typeimage.setImageResource(R.drawable.ic_baseline_network_wifivector)
                Log.d("papa", security+"1")
                networkSSID.visibility = View.VISIBLE
                encyptionType.visibility = View.VISIBLE
                if(security=="nopass"){
                    Log.d("papa", security+"3")
                    password.visibility=View.GONE
                }else{
                    Log.d("papa", security+"2")
                    password.visibility=View.VISIBLE
                }



                if ((networkSSID.editText!!.text.isNotEmpty() && security!="nopass" && password.editText!!.text.isNotEmpty()) || (
                            networkSSID.editText!!.text.isNotEmpty() && security=="nopass"
                        )) {




                    barcodeString =
                        "WIFI:T:$security;S:${networkSSID.editText!!.text};P:$security;;"
                    customBarcode= CustomBarcode(BarcodeTypes.TYPE_WIFI.name)
                    customBarcode.wifiNetworkEncryptionType=security
                    customBarcode.wifiNetworkPassword=password.editText!!.text.toString()
                    customBarcode.wifiNetworkSsid=networkSSID.editText!!.text.toString()
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.ic_baseline_network_wifivector,customBarcode,BarcodeTypes.TYPE_WIFI.nameofitem,System.currentTimeMillis(),false,false,networkSSID.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something went Wrong!",Toast.LENGTH_SHORT).show()

                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )



                }else{
                    if(security!="None" ){
                        if(networkSSID.isEmpty()){

                            Toast.makeText(requireContext(),"Input Network Name Properly",Toast.LENGTH_SHORT).show()

                        }
                        if(password.isEmpty()){

                            Toast.makeText(requireContext(),"Input Correctly",Toast.LENGTH_SHORT).show()


                        }
                    }else{
                        if(networkSSID.isEmpty()){


                            Toast.makeText(requireContext(),"Input Correctly",Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
            BarcodeTypes.TYPE_TEXT.name -> {
                typeimage.setImageResource(R.drawable.ic_baseline_textcontent)
                text.visibility = View.VISIBLE
                if (text.editText!!.text.isNotEmpty()) {
                    barcodeString = "${text.editText!!.text}"
                   customBarcode= CustomBarcode(BarcodeTypes.TYPE_TEXT.name)
                    customBarcode.textItem=text.editText!!.text.toString()
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.ic_baseline_textcontent,customBarcode,BarcodeTypes.TYPE_TEXT.nameofitem,System.currentTimeMillis(),false,false,text.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()

                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )
                }else{

                    Toast.makeText(requireContext(),"Input Text Correctly",Toast.LENGTH_SHORT).show()

                }

            }
            BarcodeTypes.TYPE_SMS.name -> {
                typeimage.setImageResource(R.drawable.ic_baseline_smsvector)
                phoneNumber.visibility = View.VISIBLE
                smsMessage.visibility = View.VISIBLE

                if (phoneNumber.editText!!.text.isNotEmpty() && smsMessage.editText!!.text.isNotEmpty()) {
                    barcodeString =
                        "smsto:${phoneNumber.editText!!.text}:${smsMessage.editText!!.text}"
                    customBarcode= CustomBarcode(BarcodeTypes.TYPE_SMS.toString())
                    customBarcode.phoneNumberOfSms=phoneNumber.editText!!.text.toString()
                    customBarcode.messageOfSms=smsMessage.editText!!.text.toString()
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.ic_baseline_smsvector,customBarcode,BarcodeTypes.TYPE_SMS.nameofitem,System.currentTimeMillis(),false,false,smsMessage.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()

                        }
                    }
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )

                }else{
                    Toast.makeText(requireContext(),"Input Fields Properly",Toast.LENGTH_SHORT).show()

                }


            }
            BarcodeTypes.TYPE_CALENDAR_EVENT.name -> {
                typeimage.setImageResource(R.drawable.ic_baseline_calendar_vector)
                starttime.visibility = View.VISIBLE
                endtime.visibility = View.VISIBLE
                description.visibility = View.VISIBLE
                eventorganizer.visibility=View.VISIBLE
                eventlocation.visibility=View.VISIBLE

                if(
                    description.editText!!.text.isNotEmpty() &&
                    eventorganizer.editText!!.text.isNotEmpty() &&
                 eventlocation.editText!!.text.isNotEmpty() &&
                    starttimelayout.editText!!.text.isNotEmpty() &&
                            endtimelayout.editText!!.text.isNotEmpty()
                ){

                   var start=finalStartDateForBarcodeString+"T"+finalStartTimeForBarcodeString+"00"
                   var end=finalEndDateForBarcodeString+"T"+finalEndTimeForBarcodeString+"00"
                   customBarcode= CustomBarcode(BarcodeTypes.TYPE_CALENDAR_EVENT.name)
                    customBarcode.startTimeEvent=finalStartDateForBarcodeString+"T"+finalStartTimeForBarcodeString+"00"
                    customBarcode.endTimeEvent=finalEndDateForBarcodeString+"T"+finalEndTimeForBarcodeString+"00"
                    customBarcode.descriptionOfEvent=description.editText!!.text.toString()
                    customBarcode.venueOfEvent=eventlocation.editText!!.text.toString()
                    customBarcode.organizerOfEvent=eventorganizer.editText!!.text.toString()
                   barcodeString= "BEGIN:VEVENT\n" +
                           "DTSTART:$start\n" +
                           "DTEND:$start\n" +
                           "DESCRIPTION:${description.editText!!.text.toString()}\n"+
                           "LOCATION:${eventlocation.editText!!.text.toString()}\n"+
                           "END:VEVENT"
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.ic_baseline_calendar_vector,customBarcode,BarcodeTypes.TYPE_CALENDAR_EVENT.nameofitem,System.currentTimeMillis(),false,false,description.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()

                        }
                    }
                    findNavController().navigate(GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(barcodeString))

                }else{

                    Toast.makeText(requireContext(),"Input Fields Properly",Toast.LENGTH_SHORT).show()

                }



            }
            "TYPE_MYCARD"->{
                typeimage.setImageResource(R.drawable.ic_baseline_add_card_24)
                name.visibility=View.VISIBLE
                phoneNumber.visibility=View.VISIBLE
                email.visibility=View.VISIBLE
                address.visibility=View.VISIBLE

                profession.visibility=View.VISIBLE

                if(name.editText!!.text.isNotEmpty() && phoneNumber.editText!!.text.isNotEmpty() &&
                    address.editText!!.text.isNotEmpty() &&
                    email.editText!!.text.isNotEmpty() && profession.editText!!.text.isNotEmpty()

                ){
                    customBarcode= CustomBarcode("TYPE_MYCARD")
                    customBarcode.name=name.editText!!.text.toString()
                    customBarcode.telephoneNumber=phoneNumber.editText!!.text.toString()
                    customBarcode.address=address.editText!!.text.toString()
                    customBarcode.email=email.editText!!.text.toString()


                    GlobalScope.launch(Dispatchers.IO) {
                        var barcodeforsaving= BarcodeForDatabase(R.drawable.phone_book_contacts_svgrepo_com,customBarcode,BarcodeTypes.TYPE_CONTACT_INFO.nameofitem,System.currentTimeMillis(),false,false,name.editText!!.text.toString()
                        )
                        barcodeforsaving.profession=profession.editText!!.text.toString()
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                               barcodeforsaving

                            )
                        }catch (e:Exception){

                         withContext(Dispatchers.Main){
                             Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()

                         }
                        }
                    }
                    barcodeString =    "MECARD:N:${name.editText!!.text};ADR:${address.editText!!.text};T:${profession.editText!!.text.toString()};TEL:${phoneNumber.editText!!.text};EMAIL:${email.editText!!.text};;"

                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )

                }else{

                        Toast.makeText(requireContext(),"Input Fields Properly",Toast.LENGTH_SHORT).show()




                }



            }
            BarcodeTypes.TYPE_CONTACT_INFO.name -> {
                typeimage.setImageResource(R.drawable.phone_book_contacts_svgrepo_com)
                name.visibility = View.VISIBLE
                phoneNumber.visibility = View.VISIBLE
                address.visibility = View.VISIBLE
                email.visibility = View.VISIBLE
                if(name.editText!!.text.isNotEmpty() && phoneNumber.editText!!.text.isNotEmpty() &&
                    address.editText!!.text.isNotEmpty() &&
                    email.editText!!.text.isNotEmpty()
                        ){
                    customBarcode= CustomBarcode(BarcodeTypes.TYPE_CONTACT_INFO.name)
                    customBarcode.name=name.editText!!.text.toString()
                    customBarcode.telephoneNumber=phoneNumber.editText!!.text.toString()
                    customBarcode.address=address.editText!!.text.toString()
                    customBarcode.email=email.editText!!.text.toString()
                    GlobalScope.launch(Dispatchers.IO) {
                        try {customBarcode.isSaved=true
                            BarcodeDatabase.getDatabase(requireContext()).barcodeDao().insertBarcode(
                                BarcodeForDatabase(R.drawable.phone_book_contacts_svgrepo_com,customBarcode,BarcodeTypes.TYPE_CONTACT_INFO.nameofitem,System.currentTimeMillis(),false,false,name.editText!!.text.toString()
                                )
                            )
                        }catch (e:Exception){

                            Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()

                        }
                    }
                    barcodeString =
                        "MECARD:N:${name.editText!!.text};ADR:${address.editText!!.text};TEL:${phoneNumber.editText!!.text};EMAIL:${email.editText!!.text};;"
                    findNavController().navigate(
                        GenerateBarcodeFragmentDirections.actionGenerateBarcodeFragmentToFinalGeneartedBarcodeFragment(
                            barcodeString
                        )
                    )

                }else{

                    Toast.makeText(requireContext(),"Input Fields Properly",Toast.LENGTH_SHORT).show()

                }

            }
        }


}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        security=securityType.getItemAtPosition(p2).toString()
        if(security!="None"){
            Log.d("papa", security+"5")
            password.visibility=View.VISIBLE
        }else{
            password.visibility=View.GONE
            Log.d("papa", security+"6")
            security="nopass"
            Log.d("papa", security+"7")
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}