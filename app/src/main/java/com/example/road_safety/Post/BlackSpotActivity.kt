package com.example.road_safety.Post

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.road_safety.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BlackSpotActivity : AppCompatActivity() {
    var db = Firebase.firestore
    var blackSpotRef = db.collection("complain")
    lateinit var adapter:PostAdapter
    lateinit var list:List<String>
    lateinit var blackSpot:ArrayList<PostModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black_spot)

        recyclerView = findViewById(R.id.recycler_view_bs)
        toolbar = findViewById(R.id.toolbar_bs)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)

        blackSpot = ArrayList()
        list = ArrayList()
        adapter = PostAdapter(blackSpot)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        setSupportActionBar(toolbar)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        }



        getBlackSpot()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }

    fun getBlackSpot(){
        blackSpotRef.get().addOnSuccessListener { docs->
            for (doc in docs.documents){
                val desc = doc.getString("desc")
                val imageUrl = doc.getString("imageUrl")
                val address = doc.getString("address")
                val time = doc.get("time") as Long
                if (!list.contains(doc.id.toString())){
                    blackSpot.add(PostModel(imageUrl as String,time,doc.id,desc as String,address as String))
                    list += doc.id.toString()
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}