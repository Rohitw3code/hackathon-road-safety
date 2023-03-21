package com.example.road_safety.Post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.road_safety.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostAdapter(private val mList: List<PostModel>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    val db = Firebase.firestore
    var complainRef = db.collection("complain")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = mList[position]
        val context = holder.itemView.context

        Glide.with(context).load(model.imageUrl).into(holder.image)
        holder.upVoteBtn.setOnClickListener{v->
            holder.upVoteValue.setText("+1")
            holder.downVoteValue.setText("0")
        }

        holder.downVoteBtn.setOnClickListener{v->
            holder.upVoteValue.setText("0")
            holder.downVoteValue.setText("-1")
        }

        holder.desc.setText(model.desc)
        holder.address.setText(model.address)


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val image: ImageView = itemView.findViewById(R.id.image_post)
        val upVoteBtn: ImageView = itemView.findViewById(R.id.up_vote_post)
        val upVoteValue: TextView = itemView.findViewById(R.id.up_vote_value_post)
        val downVoteBtn: ImageView = itemView.findViewById(R.id.down_vote_post)
        val downVoteValue: TextView = itemView.findViewById(R.id.down_vote_value_post)

        val desc: TextView = itemView.findViewById(R.id.desc_post)
        val address: TextView = itemView.findViewById(R.id.address_post)

    }

}