package com.anmolsoftwaredeveloper12345.qrcodescanner.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.anmolsoftwaredeveloper12345.qrcodescanner.adapter.SavedItemAdapter

import com.anmolsoftwaredeveloper12345.qrcodescanner.listeners.SavedItemClickHnadler
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.BarcodeForDatabase
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.CustomBarcode
import com.anmolsoftwaredeveloper12345.qrcodescanner.viewmodels.CreatedHistoryViewModel

import com.anmolsoftwaredeveloper12345.qrcodescanner.R

class CreatedFragment : Fragment() {

    lateinit var createdBarcodeListRecyclerView: RecyclerView
    private   val createdBarcodesViewModel: CreatedHistoryViewModel by activityViewModels()
    private val myAdapter: SavedItemAdapter = SavedItemAdapter(object: SavedItemClickHnadler {
        override fun handleclickisfavourite(barcode: BarcodeForDatabase) {

            createdBarcodesViewModel.updateIsFavourite(barcode)
        }

        override fun handleitemclick(customBarcode: CustomBarcode) {
            findNavController().navigate(HistoryFragmentDirections.actionHistoryFragmentToViewScannedBarcodeFragment(customBarcode))
        }

        override fun deleteclick(barcode: BarcodeForDatabase) {
          createdBarcodesViewModel.deleteBarcode(barcode)
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
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_created, container, false)
        createdBarcodeListRecyclerView=view.findViewById(R.id.createdhistory)

        var layout= LinearLayoutManager(requireContext()
            , LinearLayoutManager.VERTICAL,false)
        createdBarcodeListRecyclerView.layoutManager=layout
       createdBarcodeListRecyclerView.adapter=myAdapter

        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        createdBarcodesViewModel.createdBarcodesList.observe(viewLifecycleOwner, {


            myAdapter.bindlist(it)




        })

    }
}

