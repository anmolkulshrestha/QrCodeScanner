package com.anmolsoftwaredeveloper12345.qrcodescanner.utils


import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RateUsDialog(context: Context): Dialog(context) {
    var rating:Float=5.toFloat()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.anmolsoftwaredeveloper12345.qrcodescanner.R.layout.rateusdialog)
        
        var ratingBar=findViewById<AppCompatRatingBar>(com.anmolsoftwaredeveloper12345.qrcodescanner.R.id.ratingbar)
        var ratenow:AppCompatButton=findViewById(com.anmolsoftwaredeveloper12345.qrcodescanner.R.id.ratenow)
        var ratelater:AppCompatButton=findViewById(com.anmolsoftwaredeveloper12345.qrcodescanner.R.id.ratelater)
        var emojiimage:ImageView=findViewById(com.anmolsoftwaredeveloper12345.qrcodescanner.R.id.emoji)
        ratingBar.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, userrating, fromUser ->

           rating=userrating

        })

        ratenow.setOnClickListener {
            if(rating<=1){

                Toast.makeText(context,"Thanks For Rating Us",Toast.LENGTH_SHORT).show()
               GlobalScope.launch(Dispatchers.IO) {
                   context.getSharedPreferences("RATE_US",
                       Context.MODE_PRIVATE).let { sharedPreferences ->



                           sharedPreferences
                               .edit {

                                   putFloat("rating",rating)









                       }
                   }
               }




                this.cancel()  }
            else if(rating<=2){
                Toast.makeText(context,"Thanks For Rating Us",Toast.LENGTH_SHORT).show()
                GlobalScope.launch(Dispatchers.IO) {
                    context.getSharedPreferences("RATE_US",
                        Context.MODE_PRIVATE).let { sharedPreferences ->



                        sharedPreferences
                            .edit {

                                putFloat("rating",rating)









                            }
                    }
                }

                this.cancel() }
            else if(rating<=3){
                Toast.makeText(context,"Thanks For Rating Us",Toast.LENGTH_SHORT).show()
                GlobalScope.launch(Dispatchers.IO) {
                    context.getSharedPreferences("RATE_US",
                        Context.MODE_PRIVATE).let { sharedPreferences ->



                        sharedPreferences
                            .edit {

                                putFloat("rating",rating)









                            }
                    }
                }

                this.cancel()
            }
            else  if(rating<4){
                Toast.makeText(context,"Thanks For Rating Us",Toast.LENGTH_SHORT).show()
                GlobalScope.launch(Dispatchers.IO) {
                    context.getSharedPreferences("RATE_US",
                        Context.MODE_PRIVATE).let { sharedPreferences ->



                        sharedPreferences
                            .edit {

                                putFloat("rating",rating)









                            }
                    }
                }


                this.cancel()
            }
            else{
                GlobalScope.launch(Dispatchers.IO) {
                    context.getSharedPreferences("RATE_US",
                        Context.MODE_PRIVATE).let { sharedPreferences ->



                        sharedPreferences
                            .edit {

                                putFloat("rating",rating)









                            }
                    }
                }
                var packagE=context.packageName
                try {
                    var rateintent=Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + packagE.toString())
                    )
                   rateintent.setPackage("com.android.vending")
                    context.startActivity(
                        rateintent
                    )
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + packagE.toString())
                        )
                    )
                }
                this.cancel()
            }
        }

        ratelater.setOnClickListener {
            this.cancel()
        }
    }
}