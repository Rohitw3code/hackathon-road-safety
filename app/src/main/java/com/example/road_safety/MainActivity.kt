package com.example.road_safety

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.road_safety.Post.BlackSpotActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    private lateinit var recordBtn:Button
    private lateinit var reportBtn:Button
    private lateinit var helpBtn:Button
    private lateinit var blackBtn:Button
    private lateinit var about:TextView
    private lateinit var profile: CircleImageView
    private lateinit var firebaseAuth: FirebaseAuth
    private  var aboutClick:Boolean = false
    var db = Firebase.firestore
    var userRef = db.collection("users")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        about = findViewById(R.id.about_us)
        blackBtn = findViewById(R.id.blackspot)

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

        about.setOnClickListener { v ->
            if (aboutClick) {
                aboutClick = false
                about.textSize = 22F
                about.text = getString(R.string.about_english)
            } else {
                aboutClick = true
                about.textSize = 25F
                about.text = getString(R.string.about_hindi)
            }
        }

        recordBtn = findViewById(R.id.record) as Button
        reportBtn = findViewById(R.id.report) as Button
        helpBtn = findViewById(R.id.needHelp) as Button
        profile = findViewById(R.id.Profile)

        firebaseAuth = FirebaseAuth.getInstance()
        // set on-click listener
        blackBtn.setOnClickListener {
            val intent = Intent(this, BlackSpotActivity::class.java)
            startActivity(intent)
        }

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
        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        user()
    }

    fun user(){
        userRef.document(firebaseAuth.uid.toString())
            .get().addOnSuccessListener { doc->
                val url = doc.getString("userImageUrl").toString()
                Glide.with(this).load(url).into(profile);
            }
    }
}


