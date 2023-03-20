package com.example.road_safety

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    var db = Firebase.firestore
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    var userRef = db.collection("users")
    var adminRef = db.collection("admin")
    private lateinit var authBtn:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        authBtn = findViewById(R.id.auth_btn)

        FirebaseApp.initializeApp(this)

        authBtn = findViewById(R.id.auth_btn)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_ids))
            .requestEmail()
            .build()


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            startActivity(
                Intent(
                    this, MainActivity
                    ::class.java
                )
            )
            finish()
        }
        authBtn.setOnClickListener { view: View? ->
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "error : "+e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                createNewUser(account)
            }
        }
    }

    private fun createNewUser(account: GoogleSignInAccount) {
        val authId = FirebaseAuth.getInstance().uid.toString()

        adminRef.document("constants")
            .get().addOnSuccessListener { doc ->
                userRef.document(authId)
                    .get().addOnSuccessListener { documents ->
                        run {
                            if (!documents.exists()) {
                                val user = hashMapOf(
                                    "username" to account.displayName,
                                    "email" to account.email,
                                    "userId" to authId,
                                    "userImageUrl" to account.photoUrl,
                                    "lastActive" to System.currentTimeMillis(),
                                    "token" to "",
                                    "versionName" to BuildConfig.VERSION_NAME,
                                    "versionCode" to BuildConfig.VERSION_CODE,
                                    "address" to "",
                                    "bloodGroup" to "",
                                    "aadhaar" to ""
                                )

                                userRef.document(authId)
                                    .set(user)
                                    .addOnSuccessListener {
                                        adminRef.document("variables")
                                            .update("totalUsers", FieldValue.increment(1))
                                        Toast.makeText(
                                            this,
                                            "Welcome to RF :)",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                            } else {
                                Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }


            }


    }



}