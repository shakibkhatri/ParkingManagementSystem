package com.example.carpark_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carpark_1.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.view_lot_occupancy.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)
        supportActionBar?.hide()
        viewOccupancyButton.setOnClickListener {

            //d("nitin", "button was pressed!")
            startActivity(Intent(this, ViewLotOccupancy::class.java))
         }
    }
}



