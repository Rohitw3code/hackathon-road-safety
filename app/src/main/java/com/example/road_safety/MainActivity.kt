package com.example.road_safety

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var recordBtn:Button
    private lateinit var reportBtn:Button
    private lateinit var helpBtn:Button
    private lateinit var about:TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private  var aboutClick:Boolean = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        about = findViewById(R.id.about_us)

        getGPSLocation(this) { location ->
            if (location != null) {
                // Use the location object
                val latitude = location.latitude
                val longitude = location.longitude
                System.out.print(latitude)
                System.out.print(longitude)
                // ...
            } else {
                // Handle null result or error
                // ...
            }
        }

        about.setOnClickListener{v->
            if (aboutClick){
                aboutClick = false
                about.textSize = 22F
                about.text = getString(R.string.about_english)
            }
            else{
                aboutClick = true
                about.textSize = 25F
                about.text = getString(R.string.about_hindi)
            }
        }

        recordBtn = findViewById(R.id.record) as Button
        reportBtn = findViewById(R.id.report) as Button
        helpBtn = findViewById(R.id.needHelp) as Button

        firebaseAuth = FirebaseAuth.getInstance()
        // set on-click listener
        recordBtn.setOnClickListener {
            val intent = Intent(this, ShakeDetectorActivity::class.java)
            startActivity(intent)
        }

        reportBtn.setOnClickListener {
            val intent = Intent(this, ComplainActivity::class.java)
            startActivity(intent)
        }
        helpBtn.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
    }
}


