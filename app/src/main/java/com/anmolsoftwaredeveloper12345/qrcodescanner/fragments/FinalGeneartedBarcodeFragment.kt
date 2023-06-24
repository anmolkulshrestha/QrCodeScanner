package com.anmolsoftwaredeveloper12345.qrcodescanner.fragments

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import androidx.navigation.fragment.navArgs
import com.anmolsoftwaredeveloper12345.qrcodescanner.R


import com.anmolsoftwaredeveloper12345.qrcodescanner.utils.BarcodeBitmapGeneartor
import com.anmolsoftwaredeveloper12345.qrcodescanner.utils.FeaturesOptions

import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FinalGeneartedBarcodeFragment : Fragment() {
  val args by navArgs<FinalGeneartedBarcodeFragmentArgs>()
    lateinit var barcodeString:String
    lateinit var barcodeImage:ImageView
    lateinit var share:Button
    lateinit var save:Button
    lateinit var barcodeBitmap:Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_final_genearted_barcode, container, false)
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=View.GONE

        barcodeImage=view.findViewById(R.id.image)
        barcodeString=args.barcodestring
        share=view.findViewById(R.id.share)
        save=view.findViewById(R.id.save)

        var barcodeBitmap=BarcodeBitmapGeneartor.createBarcodeBitmap(barcodeString,200,200)
        barcodeImage.setImageBitmap(barcodeBitmap)
        save.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {

                FeaturesOptions().savePhotoToGalley(requireContext(),System.currentTimeMillis().toString()+".jpg",barcodeBitmap!!)


            }

            Toast.makeText(requireContext(),"Saved To Gallery",Toast.LENGTH_SHORT).show()
        }
        share.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                GlobalScope.launch(Dispatchers.IO) {

                    FeaturesOptions().savePhotoToInternalStorage(System.currentTimeMillis().toString()+".jpg",barcodeBitmap!!,requireContext())

                }

            }
        }

    }


}