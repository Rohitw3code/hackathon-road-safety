package com.example.road_safety

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComplainActivity : AppCompatActivity() {
    private lateinit var img:ImageView
    private lateinit var uri:String
    private lateinit var submitBtn:Button
    var db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth
    var complainRef = db.collection("complain")
    private lateinit var desc:EditText
    private lateinit var state:EditText
    private lateinit var region:EditText
    private lateinit var address:EditText
    private lateinit var toolbar: Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complain)
        firebaseAuth = FirebaseAuth.getInstance()
        desc = findViewById(R.id.desc_complain)
        state = findViewById(R.id.state_complain)
        region = findViewById(R.id.region_complain)
        address = findViewById(R.id.address_complain)
        toolbar = findViewById(R.id.toolbar_complain)

        setSupportActionBar(toolbar)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        }
        img = findViewById(R.id.image_complain)
        uri = intent.getStringExtra("uri").toString()
        img.setImageURI(Uri.parse(uri))
        submitBtn = findViewById(R.id.submit_complain)


        submitBtn.setOnClickListener{v->
            if (!desc.text.toString().isEmpty() and  !state.text.toString().isEmpty() and
                !region.text.toString().isEmpty() and !address.text.toString().isEmpty()){
                submitComplain()
            }else{
                Toast.makeText(baseContext,"No Empty field is accepted",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun submitComplain(){
        val id = System.currentTimeMillis().toString()
        var hash = hashMapOf(
            "userId" to firebaseAuth.uid.toString(),
            "time" to id,
            "desc" to desc.text.toString().trim(),
            "imageUrl" to "",
            "state" to state.text.toString().trim(),
            "region" to region.text.toString().trim(),
            "address" to address.text.toString().trim(),
        )

        complainRef.document(id).set(hash).addOnCompleteListener{
            Toast.makeText(baseContext,"done",Toast.LENGTH_SHORT).show()
            startActivity(Intent(baseContext,MainActivity::class.java))
            finish()
        }

    }


}