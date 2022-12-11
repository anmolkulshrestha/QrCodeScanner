package com.example.qrcodescanner.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.R
import com.example.qrcodescanner.adapter.SavedItemAdapter
import com.example.qrcodescanner.listeners.SavedItemClickHnadler

import com.example.qrcodescanner.models.BarcodeForDatabase
import com.example.qrcodescanner.models.CustomBarcode
import com.example.qrcodescanner.viewmodels.FavouritesViewModel

import com.google.android.material.bottomnavigation.BottomNavigationView



class FavouritesFragment : Fragment() {
    lateinit var favouritesBarcodeListRecyclerView: RecyclerView
    lateinit var   favouritesViewModel: FavouritesViewModel
    private val myAdapter: SavedItemAdapter = SavedItemAdapter( object: SavedItemClickHnadler {
        override fun handleclickisfavourite(barcode: BarcodeForDatabase) {

           favouritesViewModel.updateIsFavourite(barcode)
        }
        override fun handleitemclick(customBarcode: CustomBarcode) {
            findNavController().navigate(
              FavouritesFragmentDirections.actionFavouritesFragmentToViewScannedBarcodeFragment(customBarcode)

            )
        }

        override fun deleteclick(barcode: BarcodeForDatabase) {
          favouritesViewModel.deleteBarcode(barcode)
        }
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_favourites, container, false)
        favouritesViewModel  =ViewModelProvider(this).get(FavouritesViewModel::class.java)
        favouritesBarcodeListRecyclerView=view.findViewById(R.id.favourites)

        var layout= LinearLayoutManager(requireContext()
            , LinearLayoutManager.VERTICAL,false)
        favouritesBarcodeListRecyclerView.layoutManager=layout
        favouritesBarcodeListRecyclerView.adapter=myAdapter

        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility=View.VISIBLE
        favouritesViewModel.favouritesBarcodesList.observe(viewLifecycleOwner, {


            myAdapter.bindlist(it)




        })







    }


}