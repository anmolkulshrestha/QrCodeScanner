package com.anmolsoftwaredeveloper12345.qrcodescanner.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anmolsoftwaredeveloper12345.qrcodescanner.R

import com.anmolsoftwaredeveloper12345.qrcodescanner.adapter.SavedItemAdapter

import com.anmolsoftwaredeveloper12345.qrcodescanner.listeners.SavedItemClickHnadler
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.BarcodeForDatabase
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.CustomBarcode
import com.anmolsoftwaredeveloper12345.qrcodescanner.viewmodels.ScannedHistoryViewModel



class ScanedFragment : Fragment() {
    lateinit var scannedBarcodeListRecyclerView:RecyclerView

    private   val scannedBarcodesViewModel:ScannedHistoryViewModel by activityViewModels()
    private val myAdapter:SavedItemAdapter =SavedItemAdapter( object: SavedItemClickHnadler {
        override fun handleclickisfavourite(barcode: BarcodeForDatabase) {

            scannedBarcodesViewModel.updateIsFavourite(barcode)
        }

        override fun handleitemclick(customBarcode: CustomBarcode) {
            findNavController().navigate(HistoryFragmentDirections.actionHistoryFragmentToViewScannedBarcodeFragment(customBarcode))
        }

        override fun deleteclick(barcode: BarcodeForDatabase) {
           scannedBarcodesViewModel.deleteBarcode(barcode)
        }

    })
    var list= mutableListOf<BarcodeForDatabase>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view= inflater.inflate(R.layout.fragment_scaned, container, false)

        scannedBarcodeListRecyclerView=view.findViewById(R.id.scannedhistory)

        var layout=LinearLayoutManager(requireContext()
            ,LinearLayoutManager.VERTICAL,false)
        scannedBarcodeListRecyclerView.layoutManager=layout
        scannedBarcodeListRecyclerView.adapter=myAdapter

        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scannedBarcodesViewModel.scannedBarcodesList.observe(viewLifecycleOwner, {


            myAdapter.bindlist(it)




        })







    }

}