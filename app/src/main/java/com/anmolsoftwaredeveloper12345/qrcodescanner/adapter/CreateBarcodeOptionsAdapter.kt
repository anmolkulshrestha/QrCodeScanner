package com.anmolsoftwaredeveloper12345.qrcodescanner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anmolsoftwaredeveloper12345.qrcodescanner.listeners.CreateOptionClickHandler
import com.anmolsoftwaredeveloper12345.qrcodescanner.R
import com.anmolsoftwaredeveloper12345.qrcodescanner.models.CreateBarcodeOption

class CreateBarcodeOptionsAdapter(clickHandler: CreateOptionClickHandler): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context
    var clickHandler=clickHandler
    var createBarcodeOptionsList:List<CreateBarcodeOption> = mutableListOf<CreateBarcodeOption>()
    fun bindlist(createBarcodeOptionsList:List<CreateBarcodeOption>){
        this.createBarcodeOptionsList=createBarcodeOptionsList

    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var createBarcodeOptionImage: ImageView = itemView.findViewById(R.id.createBarcodeOptionImage)
        var createBarcodeOptionTitle: TextView = itemView.findViewById(R.id.createBarcodeOptionTitle)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.createbarcodeoptionsitem, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val createBarcodeOption=createBarcodeOptionsList[position]
        holder as ViewHolder
        holder.createBarcodeOptionImage.setImageResource(createBarcodeOption.resourcefile)
        holder.createBarcodeOptionTitle.text=createBarcodeOption.createBarcodeOptionDesc.toString()

   holder.itemView.setOnClickListener {
       clickHandler.createBarcodeOptionClickHandler(createBarcodeOption)
   }


    }

    override fun getItemCount(): Int {
        return createBarcodeOptionsList.size
    }


}