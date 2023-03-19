package com.example.road_safety

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_click_me = findViewById(R.id.record) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            val intent = Intent(this, ShakeDetector::class.java)
            startActivity(intent)
        }
    }
}


