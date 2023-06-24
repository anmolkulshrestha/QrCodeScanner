package com.anmolsoftwaredeveloper12345.qrcodescanner.fragments

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import com.anmolsoftwaredeveloper12345.qrcodescanner.R


import com.anmolsoftwaredeveloper12345.qrcodescanner.utils.FeaturesOptions
import com.anmolsoftwaredeveloper12345.qrcodescanner.utils.RateUsDialog


import com.google.android.material.bottomnavigation.BottomNavigationView



class SettingsFragment : Fragment() {

    lateinit var history: Switch
    lateinit var contactus: TextView
    lateinit var rateus:TextView



    lateinit var privacypolicy: TextView

    var ishistory: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=View.VISIBLE
        // Inflate the layout for this fragment
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        var view = inflater.inflate(R.layout.fragment_settings, container, false)

       history = view.findViewById(R.id.savehistory)
        contactus = view.findViewById(R.id.contactus)
       privacypolicy = view.findViewById(R.id.privacypolicy)
        rateus=view.findViewById(R.id.rateus)
     rateus.setOnClickListener {
    var dialog= RateUsDialog(requireContext())
    dialog.setCancelable(false)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
       }
      requireContext().getSharedPreferences("SAVE_HISTORY",
            Context.MODE_PRIVATE).let { sharedPreferences ->
            if(sharedPreferences.contains("isSaveHistory")){
              ishistory=  sharedPreferences.getBoolean("isSaveHistory",true)

            }else{
            sharedPreferences.edit().putBoolean("isSaveHistory",true)
                ishistory=true



            }
        }
        history.isChecked=ishistory

        contactus.setOnClickListener {
            FeaturesOptions().sendemail(requireContext())
        }
        privacypolicy.setOnClickListener {
            FeaturesOptions().openOnWeb("https://qrscannerprivacypolicy-yty6.vercel.app/",requireContext())
        }

      history.setOnCheckedChangeListener { compoundButton, b ->
          if(history.isChecked){
              Toast.makeText(requireContext(),"History is enabled", Toast.LENGTH_SHORT).show()
              requireContext().getSharedPreferences("SAVE_HISTORY", Context.MODE_PRIVATE)
                  .edit {
                      putBoolean("isSaveHistory", true)
                      ishistory = true
                  }


          }
          else{
              Toast.makeText(requireContext(),"History is disabled", Toast.LENGTH_SHORT).show()
              requireContext().getSharedPreferences("SAVE_HISTORY", Context.MODE_PRIVATE)
                  .edit {
                      putBoolean("isSaveHistory",false)
                      ishistory = false
                  }
          }
      }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}