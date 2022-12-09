package com.example.qrcodescanner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.FeatureClickHandler
import com.example.qrcodescanner.R
import com.example.qrcodescanner.models.BarcodeFeatures
import com.example.qrcodescanner.models.BarcodeTile

class ScannedBarcodeFeaturesAdapter(clickHandler: FeatureClickHandler): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context
    var clickHandler=clickHandler
    var barcodeFeatures:List<BarcodeFeatures> = mutableListOf<BarcodeFeatures>()
    fun bindlist(barcodefeatures:List<BarcodeFeatures>){
        this.barcodeFeatures=barcodefeatures

    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var featureimage: ImageView= itemView.findViewById(R.id.featureimage)
        var featuredesc: TextView = itemView.findViewById(R.id.featurediscription)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.barcodefeaturelisttile, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val barcodefeature=barcodeFeatures[position]
        holder as ViewHolder
        holder.featureimage.setImageResource(barcodefeature.resourcefile)
        holder.featuredesc.text=barcodefeature.featuredesc.toString()

        holder.itemView.setOnClickListener {
            clickHandler.click(barcodefeature)
        }


    }

    override fun getItemCount(): Int {
        return barcodeFeatures.size
    }


}