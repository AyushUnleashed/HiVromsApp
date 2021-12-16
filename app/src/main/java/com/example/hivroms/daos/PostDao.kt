package com.example.hivroms.daos

import android.util.Log
import android.widget.Toast
import com.example.hivroms.models.PostModel
import com.example.hivroms.models.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("posts")
    val auth = Firebase.auth

    fun addPost(text:String)
    {
        //Log.d("RAJNI","PostCreated")
        //Toast.makeText(this@PostDao,"adding post", Toast.LENGTH_LONG).show()
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid

            val userDao = UserDao()

            val user = userDao.getUserById(currentUserId).await().toObject(UserModel::class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = PostModel(text,user,currentTime)
            postCollection.document().set(post)
        }
    }

    fun getPostById(postId: String): Task<DocumentSnapshot>
    {
        return postCollection.document(postId).get()
    }

    fun updateLikes(postId: String)
    {
        //getting the post through its id
        GlobalScope.launch {
            //getting current user id
            val currentUserId = auth.currentUser!!.uid

            //getting our post of which we need to increase likes
            val post = getPostById(postId).await().toObject(PostModel::class.java)

            // if user is present in likedBy array list //contains function on arraylist
            val isLiked = post?.likedBy?.contains(currentUserId)

            if(isLiked == true) //user has already like the post
            {
                post.likedBy.remove(currentUserId) //then unlike | remove user
            }
            else //user have not like the post
            {
                if (post != null) {
                    post.likedBy.add(currentUserId)
                } //then like the post | add user
            }

            if (post != null) {
                postCollection.document(postId).set(post)
            }
        }


    }
}