package com.example.qrcodescanner.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.CreateOptionClickHandler
import com.example.qrcodescanner.R
import com.example.qrcodescanner.adapter.CreateBarcodeOptionsAdapter
import com.example.qrcodescanner.models.CreateBarcodeOption
import com.example.qrcodescanner.viewmodels.ScannedHistoryViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_create.*


class CreateFragment : Fragment() {

  lateinit var createBarcodeOptionsRecyclerView:RecyclerView
  var createBrcodeOptionsList= mutableListOf<CreateBarcodeOption>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_create, container, false)
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=View.VISIBLE

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createBarcodeOptionsRecyclerView=view.findViewById(R.id.createBarcodeOptions)
        initializeCreateBarcodeOptionsList()
        var featuresLayoutManager= GridLayoutManager(activity,4)
        var optionadapter=CreateBarcodeOptionsAdapter(object:CreateOptionClickHandler{
            override fun createBarcodeOptionClickHandler(createBarcodeOption: CreateBarcodeOption) {
                findNavController().navigate(CreateFragmentDirections.actionCreateFragmentToGenerateBarcodeFragment(createBarcodeOption))
                createBrcodeOptionsList.clear()
            }
        })
        optionadapter.bindlist(createBrcodeOptionsList)
        createBarcodeOptionsRecyclerView.layoutManager=featuresLayoutManager
        createBarcodeOptionsRecyclerView.adapter=optionadapter


    }

    fun initializeCreateBarcodeOptionsList(){
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_WIFI","Wi-fi",R.drawable.ic_baseline_network_wifivector))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_TEXT","Text", R.drawable.ic_baseline_textcontent))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_URL","Website",R.drawable.web_search_4291))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_EMAIL","E-mail",R.drawable.ic_baseline_emailvector))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_PHONE","Tel",R.drawable.ic_baseline_callvector))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_SMS","SMS",R.drawable.ic_baseline_smsvector))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_CONTACT_INFO","Contacts",R.drawable.phone_book_contacts_svgrepo_com))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_CALENDAR_EVENT","Calendar"+"\n"+"Event",R.drawable.ic_baseline_calendar_vector))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_MYCARD","My Card",R.drawable.ic_baseline_add_card_24))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_WHATSAPP","WhatsApp",R.drawable.icons8_whatsapp))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_INSTAGRAM","Instagram",R.drawable.icons8_instagram))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_TWITTER","Twitter",R.drawable.icons8_twittervector))
        createBrcodeOptionsList.add(CreateBarcodeOption("TYPE_PAYPAL","PayPal",R.drawable.icons8_paypalvector))

    }

}