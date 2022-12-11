package com.example.qrcodescanner.adapter

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.R
import com.example.qrcodescanner.listeners.SavedItemClickHnadler
import com.example.qrcodescanner.models.BarcodeForDatabase


class SavedItemAdapter(savedItemClickHnadler: SavedItemClickHnadler): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context
    var clickHnadler=savedItemClickHnadler
    var savedBarcodes:List<BarcodeForDatabase> = mutableListOf<BarcodeForDatabase>()
    fun bindlist(savedbarcodes:List<BarcodeForDatabase>){
     savedBarcodes=savedbarcodes
        for(item in savedbarcodes){
            Log.d("saveditems", item.toString())
        }
        notifyDataSetChanged()

    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var savedname: TextView = itemView.findViewById(R.id.savedname)
        var savedtype: TextView = itemView.findViewById(R.id.savedtype)
        var savedimage:ImageView=itemView.findViewById(R.id.savedtypeimage)
        var isfavourite:CheckBox=itemView.findViewById(R.id.isfavourite)
        var date:TextView=itemView.findViewById(R.id.date)
        var delete:ImageView=itemView.findViewById(R.id.delete)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context=parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.savedbarcodeitem, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val savedbarcode=savedBarcodes[position]
        holder as ViewHolder
        holder.savedname.text=savedbarcode.nameOfBarcode
        holder.savedtype.text=savedbarcode.barcodeType
        holder.date.text=DateFormat.format("dd-MMM-yyyy hh:mm a", savedbarcode.date).toString()
        Log.d("mahan", DateFormat.format("dd-MMM-yyyy hh:mm a", savedbarcode.date).toString())
        holder.savedimage.setImageResource(savedbarcode.iconId)
        if(savedbarcode.isFavourite){
            holder.isfavourite.isChecked=true
        }else{
            holder.isfavourite.isChecked=false
        }

        holder.isfavourite.setOnClickListener {
            if(savedbarcode.isFavourite){
                savedbarcode.isFavourite=false
                clickHnadler.handleclickisfavourite(savedbarcode)
            }else{
                savedbarcode.isFavourite=true
                clickHnadler.handleclickisfavourite(savedbarcode)
            }


        }
        holder.delete.setOnClickListener {
            clickHnadler.deleteclick(savedbarcode)





        }

        holder.savedimage.setOnClickListener {
            clickHnadler.handleitemclick(savedbarcode.customBarcode)
        }
        holder.date.setOnClickListener {
            clickHnadler.handleitemclick(savedbarcode.customBarcode)
        }
        holder.savedname.setOnClickListener {
            clickHnadler.handleitemclick(savedbarcode.customBarcode)
        }
        holder.savedtype.setOnClickListener {
            clickHnadler.handleitemclick(savedbarcode.customBarcode)
        }

    }

    override fun getItemCount(): Int {
        Log.d("listsize", savedBarcodes.size.toString())
        return savedBarcodes.size

    }


}