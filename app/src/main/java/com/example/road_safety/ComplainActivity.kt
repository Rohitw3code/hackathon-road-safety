package com.example.road_safety

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
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
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complain)
        firebaseAuth = FirebaseAuth.getInstance()
        img = findViewById(R.id.image_complain)
        uri = intent.getStringExtra("uri").toString()
        img.setImageURI(Uri.parse(uri))
        submitBtn = findViewById(R.id.submit_complain)


        submitBtn.setOnClickListener{v->
            submitComplain()
        }

    }

    fun submitComplain(){
        val id = System.currentTimeMillis().toString()
        var hash = hashMapOf(
            "userId" to firebaseAuth.uid.toString(),
            "time" to id,
            "desc" to "",
            "imageUrl" to "",
            "state" to "",
            "region" to "",
            "address" to "",
        )

        complainRef.document(id).set(hash).addOnCompleteListener{
            Toast.makeText(baseContext,"done",Toast.LENGTH_SHORT).show()
        }





    }


}