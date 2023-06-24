package com.anmolsoftwaredeveloper12345.qrcodescanner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anmolsoftwaredeveloper12345.qrcodescanner.R
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.BarcodeTile
import com.google.android.gms.common.config.GservicesValue.value
import kotlinx.android.synthetic.main.barcodecontenttile.view.*

class ScannedBarcodeContentAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context
    var barcodeTiles:List<BarcodeTile> = mutableListOf<BarcodeTile>()
    fun bindlist(barcodetiles:List<BarcodeTile>){
        this.barcodeTiles=barcodetiles

    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var header: TextView = itemView.findViewById(R.id.header)

        var value: TextView = itemView.findViewById(R.id.value)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.barcodecontenttile, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      val barcode=barcodeTiles[position]
        holder as ViewHolder
        holder.header.text=barcode.header.toString()
        holder.value.text=barcode.value.toString()
    }

    override fun getItemCount(): Int {
        return barcodeTiles.size
    }


}