package com.example.road_safety

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.ImageView


class profileActivity : AppCompatActivity() {

    private lateinit var logout: ImageView
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logout = findViewById(R.id.logout_btn)


        logout.setOnClickListener{v->
            firebaseAuth.signOut()
            startActivity(Intent(baseContext,AuthActivity::class.java))
            finish()
        }

    }
}