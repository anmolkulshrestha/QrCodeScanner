package com.example.qrcodescanner

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.adapter.SavedItemAdapter
import com.example.qrcodescanner.database.BarcodeDatabase
import com.example.qrcodescanner.fragments.HistoryFragmentDirections
import com.example.qrcodescanner.models.BarcodeForDatabase
import com.example.qrcodescanner.models.CustomBarcode
import com.example.qrcodescanner.viewmodels.ScannedHistoryViewModel
import kotlinx.android.synthetic.main.fragment_scaned.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ScanedFragment : Fragment() {
    lateinit var scannedBarcodeListRecyclerView:RecyclerView

    private   val scannedBarcodesViewModel:ScannedHistoryViewModel by activityViewModels()
    private val myAdapter:SavedItemAdapter =SavedItemAdapter( object:SavedItemClickHnadler{
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
        Log.d("bhen", "onCreted")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("bhen", "onCretedView")
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_scaned, container, false)

 //       scannedBarcodesViewModel=ViewModelProvider(requireActivity()).get(ScannedHistoryViewModel::class.java)

        scannedBarcodeListRecyclerView=view.findViewById(R.id.scannedhistory)

        var layout=LinearLayoutManager(requireContext()
            ,LinearLayoutManager.VERTICAL,false)
        scannedBarcodeListRecyclerView.layoutManager=layout
        scannedBarcodeListRecyclerView.adapter=myAdapter

        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

     //   scannedBarcodesViewModel=ViewModelProvider(this).get(ScannedHistoryViewModel::class.java)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scannedBarcodesViewModel.scannedBarcodesList.observe(viewLifecycleOwner, {


            myAdapter.bindlist(it)



            for(item in it){
                Log.d("whynow", item.toString())
            }
        })
        Log.d("bhen", "onCretedpappu")






    }

}