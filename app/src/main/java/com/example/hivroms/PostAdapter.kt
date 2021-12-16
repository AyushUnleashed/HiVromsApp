package com.example.hivroms

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hivroms.daos.PostDao
import com.example.hivroms.models.PostModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapter(options: FirestoreRecyclerOptions<PostModel>, val listener: IPostAdapter) :
    FirestoreRecyclerAdapter<PostModel, PostAdapter.PostViewHolder>(options)
{
    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val userName: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val postTitle: TextView = itemView.findViewById(R.id.postTitle)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context).
                inflate(R.layout.item_post,parent,false))

        viewHolder.likeButton.setOnClickListener {
            //getting post id
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.absoluteAdapterPosition).id)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: PostModel) {

        holder.postTitle.text = model.text
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.userName.text = model.createdBy.displayName
        holder.likeCount.text = model.likedBy.size.toString()

        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid

        val isLiked = model.likedBy.contains(currentUserId)

        if(isLiked) //user has already like the post
        {
            holder.likeButton.setImageDrawable(ContextCompat.
            getDrawable(holder.likeButton.context,R.drawable.ic_liked))
        }
        else //user have not like the post
        {
            holder.likeButton.setImageDrawable(ContextCompat.
            getDrawable(holder.likeButton.context,R.drawable.ic_unliked))
            //then like the post | add user
        }
    }

    interface IPostAdapter{
        fun onLikeClicked(postId : String)
    }
}

