package com.example.skku_parkinglot

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        // Firebase reference
//        val database = FirebaseDatabase.getInstance()
//        val refSectorA = database.getReference("SectorA")
//        val refSectorB = database.getReference("SectorB")

        val button_sectorA = findViewById<Button>(R.id.button_sectorA)
        val button_sectorB = findViewById<Button>(R.id.button_sectorB)
        var grid_sectorA = findViewById<GridLayout>(R.id.grid_sectorA)
        var grid_sectorB = findViewById<LinearLayout>(R.id.grid_sectorB)
        var sector_a_image = findViewById<ImageView>(R.id.sector_a_image)
        var sector_b_image = findViewById<ImageView>(R.id.sector_b_image)
        // Set up button listeners for sector selection
        button_sectorA.setOnClickListener {
            grid_sectorA.visibility = View.VISIBLE
            grid_sectorB.visibility = View.GONE
            sector_a_image.visibility = View.VISIBLE
            sector_b_image.visibility = View.GONE
            button_sectorB.setBackgroundColor(Color.parseColor("#97B9A0"))
            button_sectorA.setBackgroundColor(Color.parseColor("#487655"))
            //loadSectorData(refSectorA, "spot_")
        }

        button_sectorB.setOnClickListener {
            grid_sectorA.visibility = View.GONE
            grid_sectorB.visibility = View.VISIBLE
            sector_a_image.visibility = View.GONE
            sector_b_image.visibility = View.VISIBLE
            button_sectorB.setBackgroundColor(Color.parseColor("#487655"))
            button_sectorA.setBackgroundColor(Color.parseColor("#97B9A0"))

            //loadSectorData(refSectorB, "spot_")
        }
    }

//    private fun loadSectorData(ref: DatabaseReference, prefix: String) {
//        ref.get().addOnSuccessListener { dataSnapshot ->
//            for (spot in dataSnapshot.children) {
//                val id = spot.key?.toInt() ?: continue
//                val status = spot.getValue(Int::class.java) ?: continue
//                val cellId = resources.getIdentifier("${prefix}${id}", "id", packageName)
//                val cellView = findViewById<View>(cellId)
//
//                when (status) {
//                    0 -> cellView.setBackgroundColor(Color.parseColor("#D32F2F")) // Full
//                    1 -> cellView.setBackgroundColor(Color.parseColor("#388E3C")) // Available
//                    2 -> cellView.setBackgroundColor(Color.parseColor("#FBC02D")) // Tight
//                    3 -> cellView.setBackgroundColor(Color.parseColor("#BDBDBD")) // Unknown
//                }
//            }
//        }
//    }
}