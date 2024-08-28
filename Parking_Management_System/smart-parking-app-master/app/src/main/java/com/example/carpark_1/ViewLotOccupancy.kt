package com.example.carpark_1

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.view_lot_occupancy.*
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset.UTC


var lotID: Int? = 0
var length: Int? = 0
var statusStr: String = " "
val database = Firebase.database
var time: Long = 0

class ViewLotOccupancy: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.view_lot_occupancy)
        readDatabase()

   }

    fun updateSlots(statusStr: String, time: Long) {
        var status = statusStr.removeSurrounding("[", "]").split(",").map { it.toInt() }

        var img_arr = Array(19) {
            R.drawable.car_vacant
        }
        for (i in 0..18) {
            if (status[i] == 0) {
                img_arr[i] = R.drawable.car_occupied
            }
        }
        for (i in 0..18) {
            var currentLot = "lotView$i"
            var currentID: Int = resources.getIdentifier(currentLot, "id", packageName)
            var currentview: ImageView = findViewById<ImageView>(currentID) as ImageView
            currentview.setImageResource(img_arr[i])
        }
        var tm = Instant.ofEpochSecond(time).atZone(UTC).toLocalTime().toString()
        timeValue.text = tm
   }

    fun readDatabase() {

        // Declare and reference the database
        val myRef = database.getReference("TestData")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var indSnapshot: DataSnapshot? = null
                var len = dataSnapshot.childrenCount.toInt()

                var iter: Int = 0
                for (indSnapshot in dataSnapshot.children){     //iterate through independent snapshots
                    if (iter == len - 1){   // last data in database snapshot
                        lotID = indSnapshot.child("lot ID").getValue<Int>()
                        length = indSnapshot.child("length").getValue<Int>()
                        statusStr = indSnapshot.child("status").getValue<String>()!!
                        time = indSnapshot.child("timestamp").getValue<Long>()!!
                    }
                    iter += 1
                }
                updateSlots(statusStr, time)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.d("DEBUG", "Failed to read value.", error.toException())
            }
        })
    }
}

