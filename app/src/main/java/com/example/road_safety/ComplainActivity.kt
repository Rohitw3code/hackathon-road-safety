package com.example.road_safety

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

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
    private lateinit var progressBar: ProgressBar

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
        progressBar = findViewById(R.id.progress_bar_complain)

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
                uploadImage()
            }else{
                Toast.makeText(baseContext,"No Empty field is accepted",Toast.LENGTH_SHORT).show()
            }
        }

    }


    @SuppressLint("Range")
    fun getFileNameFromUri(context: Context, uri: Uri): String {
        var fileName = ""
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
            it.close()
        }
        return fileName
    }

    fun uploadImage(){
        progressBar.visibility = View.VISIBLE
        val sd = getFileNameFromUri(applicationContext, Uri.parse(uri)!!)
        var storageRef = Firebase.storage.reference;
        // Upload Task with upload to directory 'file'
        // and name of the file remains same
        val uploadTask = storageRef.child("file/$sd").putFile(Uri.parse(uri))

        // On success, download the file URL and display it
        uploadTask.addOnSuccessListener {
            // using glide library to display the image
            storageRef.child("upload/$sd").downloadUrl.addOnSuccessListener {
                submitComplain(it.toString())
                Log.e("Firebase", "download passed"+it)
            }.addOnFailureListener {
                Toast.makeText(baseContext,"Failed in downloading",Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }.addOnFailureListener {it->
            Toast.makeText(baseContext,""+it,Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
        }
    }
    fun submitComplain(url:String){
        val id = System.currentTimeMillis().toString()
        var hash = hashMapOf(
            "userId" to firebaseAuth.uid.toString(),
            "time" to id,
            "desc" to desc.text.toString().trim(),
            "imageUrl" to url,
            "state" to state.text.toString().trim().lowercase(),
            "region" to region.text.toString().trim().lowercase(),
            "address" to address.text.toString().trim().lowercase(),
        )

        complainRef.document(id).set(hash).addOnCompleteListener{
            Toast.makeText(baseContext,"done",Toast.LENGTH_SHORT).show()
            startActivity(Intent(baseContext,MainActivity::class.java))
            finish()
            progressBar.visibility = View.GONE
        }





    }


}