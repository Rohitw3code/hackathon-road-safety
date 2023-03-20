package com.example.road_safety

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView


class ProfileActivity : AppCompatActivity() {

    private lateinit var logout: Button
    private lateinit var firebaseAuth: FirebaseAuth
    var db = Firebase.firestore
    var userRef = db.collection("users")
    private lateinit var name:EditText
    private lateinit var aadhaar:EditText
    private lateinit var bgp:EditText
    private lateinit var address:EditText
    private lateinit var image: CircleImageView
    private lateinit var update:Button
    private lateinit var toolbar:Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logout = findViewById(R.id.logout_btn)
        firebaseAuth = FirebaseAuth.getInstance()

        toolbar = findViewById(R.id.toolbar_profile)
        name = findViewById(R.id.name_profile)
        aadhaar = findViewById(R.id.aadhaar_profile)
        bgp = findViewById(R.id.blood_group_profile)
        address = findViewById(R.id.address_profile)
        image = findViewById(R.id.image_profile)
        update = findViewById(R.id.Submit_button)

        setSupportActionBar(toolbar)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        }

        logout.setOnClickListener{v->
            firebaseAuth.signOut()
            startActivity(Intent(baseContext,AuthActivity::class.java))
            finish()
        }

        update.setOnClickListener{v->
            update()
        }

        user()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }

    fun update(){
        val hash = hashMapOf(
            "username" to name.text.toString().trim(),
            "versionName" to BuildConfig.VERSION_NAME,
            "versionCode" to BuildConfig.VERSION_CODE,
            "address" to address.text.toString().trim(),
            "bloodGroup" to bgp.text.toString().trim(),
            "aadhaar" to aadhaar.text.toString().trim()
        )
        userRef.document(firebaseAuth.uid.toString())
            .update(hash as Map<String, Any>).addOnSuccessListener {
                Toast.makeText(baseContext,"updated!",Toast.LENGTH_SHORT).show()
            }
    }


    fun user(){
        userRef.document(firebaseAuth.uid.toString())
            .get().addOnSuccessListener { doc->
                val url = doc.getString("userImageUrl").toString()
                val name_v = doc.getString("username").toString()
                val bloodgroup_v = doc.getString("bloodGroup").toString()
                val aadhaar_v = doc.getString("aadhaar").toString()
                val address_v = doc.getString("address").toString()
                Glide.with(this).load(url).into(image)
                name.setText(name_v)
                bgp.setText(bloodgroup_v)
                aadhaar.setText(aadhaar_v)
                address.setText(address_v)

            }
    }

}