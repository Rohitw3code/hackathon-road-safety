package com.example.road_safety

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ComplainActivity : AppCompatActivity() {
    private lateinit var img:ImageView
    private lateinit var uri:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complain)
        img = findViewById(R.id.image_complain)
        uri = intent.getStringExtra("uri").toString()

        img.setImageURI(Uri.parse(uri))





    }
}