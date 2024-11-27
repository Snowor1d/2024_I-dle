//package com.example.skku_parkinglot
//
//import android.graphics.Color
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.GridLayout
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.Toast
//import androidx.core.content.ContextCompat
//import com.google.firebase.firestore.FirebaseFirestore
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val db = FirebaseFirestore.getInstance()
//
//        val refSectorA = db.collection("vacant").document("SectorA")
//        refSectorA.get().addOnSuccessListener { document ->
//            if (document != null) {
//                for (i in 1..474){
//                    val fieldID = "id_$i"
//                    val spotId = resources.getIdentifier("spot_$i", "id", packageName)
//                    val view = findViewById<View>(spotId)
//
//                    document.getLong(fieldID)?.toInt()?.let {value ->
//                        view?.let{
//                            updateBackgroundColor(value, it)
//                        }
//
//                    }
//                }
//
//            } else {
//                Toast.makeText(this, "Document not found", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener{ exception ->
//            Toast.makeText(this, "Error Fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
//            Log.d("BasycSyntax","${exception.message}")
//
//        }
//
//
//        val button_sectorA = findViewById<Button>(R.id.button_sectorA)
//        val button_sectorB = findViewById<Button>(R.id.button_sectorB)
//        var grid_sectorA = findViewById<GridLayout>(R.id.grid_sectorA)
//        var grid_sectorB = findViewById<LinearLayout>(R.id.grid_sectorB)
//        var sector_a_image = findViewById<ImageView>(R.id.sector_a_image)
//        var sector_b_image = findViewById<ImageView>(R.id.sector_b_image)
//        // Set up button listeners for sector selection
//        button_sectorA.setOnClickListener {
//            grid_sectorA.visibility = View.VISIBLE
//            grid_sectorB.visibility = View.GONE
//            sector_a_image.visibility = View.VISIBLE
//            sector_b_image.visibility = View.GONE
//            button_sectorB.setBackgroundColor(Color.parseColor("#97B9A0"))
//            button_sectorA.setBackgroundColor(Color.parseColor("#487655"))
//        }
//
//        button_sectorB.setOnClickListener {
//            grid_sectorA.visibility = View.GONE
//            grid_sectorB.visibility = View.VISIBLE
//            sector_a_image.visibility = View.GONE
//            sector_b_image.visibility = View.VISIBLE
//            button_sectorB.setBackgroundColor(Color.parseColor("#487655"))
//            button_sectorA.setBackgroundColor(Color.parseColor("#97B9A0"))
//        }
//    }
//
//    private fun updateBackgroundColor(value: Int, view: View) {
//        val colorResId = when (value) {
//            0 -> R.color.available   // Green for available
//            1 -> R.color.full        // Red for full
//            2 -> R.color.tight       // Yellow for tight
//            else -> R.color.unknown  // Gray for unknown state
//        }
//        view.setBackgroundColor(ContextCompat.getColor(this, colorResId))
//    }
//}
//
package com.example.skku_parkinglot

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private val refreshInterval = 5000L // Default 10 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()

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
        }

        button_sectorB.setOnClickListener {
            grid_sectorA.visibility = View.GONE
            grid_sectorB.visibility = View.VISIBLE
            sector_a_image.visibility = View.GONE
            sector_b_image.visibility = View.VISIBLE
            button_sectorB.setBackgroundColor(Color.parseColor("#487655"))
            button_sectorA.setBackgroundColor(Color.parseColor("#97B9A0"))
        }

        // Start periodic updates
        runnable = object : Runnable {
            override fun run() {
                fetchAndUpdateData(db)
                handler.postDelayed(this, refreshInterval) // Schedule the next execution
            }
        }
        handler.post(runnable)
    }

    private fun fetchAndUpdateData(db: FirebaseFirestore) {
        val refSectorA = db.collection("vacant").document("SectorA")
        refSectorA.get().addOnSuccessListener { document ->
            if (document != null) {
                for (i in 1..474) {
                    val fieldID = "id_$i"
                    val spotId = resources.getIdentifier("spot_$i", "id", packageName)
                    val view = findViewById<View>(spotId)

                    document.getLong(fieldID)?.toInt()?.let { value ->
                        view?.let {
                            updateBackgroundColor(value, it)
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Document not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error Fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
            Log.d("MainActivity", "${exception.message}")
        }
    }

    private fun updateBackgroundColor(value: Int, view: View) {
        val colorResId = when (value) {
            0 -> R.color.available   // Green for available
            1 -> R.color.full        // Red for full
            2 -> R.color.tight       // Yellow for tight
            else -> R.color.unknown  // Gray for unknown state
        }
        view.setBackgroundColor(ContextCompat.getColor(this, colorResId))
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Stop the periodic updates when the activity is destroyed
    }
}