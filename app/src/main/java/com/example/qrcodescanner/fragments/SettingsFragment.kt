package com.example.qrcodescanner.fragments

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Switch
import android.widget.TextView
import com.example.qrcodescanner.R
import com.example.qrcodescanner.featureslist.FeaturesOptions
import com.google.android.material.bottomnavigation.BottomNavigationView


class SettingsFragment : Fragment() {

    lateinit var history: Switch
    lateinit var contactus: TextView
    lateinit var privacypolicy: TextView
    lateinit var about: TextView


    lateinit var privacypolicyDialog: Dialog

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
        about = view.findViewById(R.id.about)

        setUpPrivacyPolicyDialog()
        contactus.setOnClickListener {
            FeaturesOptions().sendemail(requireContext())
        }
        privacypolicy.setOnClickListener { privacypolicyDialog.show() }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun setUpPrivacyPolicyDialog(){
        privacypolicyDialog = Dialog(requireContext())
        privacypolicyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        privacypolicyDialog.setContentView(R.layout.privacypolicy_dialog)







    }

}