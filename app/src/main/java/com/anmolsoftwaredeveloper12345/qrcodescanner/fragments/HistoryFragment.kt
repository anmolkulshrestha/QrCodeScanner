package com.anmolsoftwaredeveloper12345.qrcodescanner.fragments

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.anmolsoftwaredeveloper12345.qrcodescanner.R
import com.anmolsoftwaredeveloper12345.qrcodescanner.utils.FeaturesOptions
import com.anmolsoftwaredeveloper12345.qrcodescanner.utils.RateUsDialog


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


val animalsArray = arrayOf(
    "Scanned",
    "Created"

)

class HistoryFragment : Fragment() {
    lateinit var pager:ViewPager2
    lateinit var tabLayout: TabLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(FeaturesOptions().sharedPreferencesForRateUs(requireContext())<(4).toFloat()){


            var dialog= RateUsDialog(requireContext())
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=View.GONE
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        var view= inflater.inflate(R.layout.fragment_history, container, false)
        pager=view.findViewById(R.id.pager)
        tabLayout=view.findViewById(R.id.tablayout)
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        pager.adapter=adapter
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = animalsArray[position]
        }.attach()


        return view
    }


}

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(
    fragmentManager,lifecycle
){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> return ScanedFragment()


        }
        return  CreatedFragment()

    }

}