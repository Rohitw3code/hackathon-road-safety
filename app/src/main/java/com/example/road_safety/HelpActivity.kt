package com.example.road_safety

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar


class HelpActivity : AppCompatActivity() {
    private lateinit var policeBtn:Button
    private lateinit var ambulanceBtn:Button
    private lateinit var fireBtn:Button
    private lateinit var emergencyBtn:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        policeBtn = findViewById(R.id.police_help)
        ambulanceBtn = findViewById(R.id.ambulance_help)
        fireBtn = findViewById(R.id.fire_brigade_help)
        emergencyBtn = findViewById(R.id.emergency_help)

        policeBtn.setOnClickListener{v->
            Snackbar.make(policeBtn,"You Location has been sent to police ",Snackbar.LENGTH_INDEFINITE).show()
        }
        ambulanceBtn.setOnClickListener{v->
            Snackbar.make(ambulanceBtn,"You Location has been sent to Ambulance ",Snackbar.LENGTH_INDEFINITE).show()
        }
        fireBtn.setOnClickListener{v->
            Snackbar.make(fireBtn,"You Location has been sent to Fire brigade",Snackbar.LENGTH_INDEFINITE).show()
        }
        emergencyBtn.setOnClickListener{v->
            Snackbar.make(emergencyBtn,"You Location has been sent to police and ambulance ",Snackbar.LENGTH_INDEFINITE).show()
        }

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


    }
}








