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
    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var logout:ImageView
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
        logout = findViewById(R.id.logout_btn)

        firebaseAuth = FirebaseAuth.getInstance()
        // set on-click listener
        recordBtn.setOnClickListener {
            val intent = Intent(this, ShakeDetectorActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener{v->
            firebaseAuth.signOut()
            startActivity(Intent(baseContext,AuthActivity::class.java))
            finish()
        }

        reportBtn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            gallery.setType("image/* video/*")
            startActivityForResult(gallery, pickImage)
//            ImagePicker.with(this)
//                .cameraOnly().crop().maxResultSize(400,400).start()
        }
        helpBtn.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val cintent = Intent(baseContext,ComplainActivity::class.java)
            cintent.putExtra("uri",imageUri.toString())
            startActivity(cintent)
        }
    }
}


