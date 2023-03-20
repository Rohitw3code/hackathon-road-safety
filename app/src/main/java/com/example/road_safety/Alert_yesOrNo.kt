package com.example.road_safety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Alert_yesOrNo : AppCompatActivity() {
    private lateinit var noBtn: Button
    private lateinit var yesBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_yes_or_no)

        noBtn = findViewById(R.id.nobtn) as Button
        yesBtn = findViewById(R.id.yesbtn) as Button

        noBtn.setOnClickListener {
            finish()
            System.out.close()
        }
        yesBtn.setOnClickListener{
            finish()
            System.out.close()
        }
    }

}