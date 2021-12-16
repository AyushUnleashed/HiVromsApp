package com.example.hivroms

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hivroms.daos.PostDao
import com.example.hivroms.models.PostModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.api.Distribution
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.Query
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*

class MainActivity : AppCompatActivity(), PostAdapter.IPostAdapter {

    private  lateinit var postDao:PostDao
    private lateinit var mAuth: FirebaseAuth

    private  lateinit var adapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setSupportActionBar(myToolBar)

        mAuth= Firebase.auth
        val currentUser = mAuth.currentUser

        postDao = PostDao()
        fab.setOnClickListener {
            val intent= Intent(this,CreatePostActivity::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()

    }

    private fun setUpRecyclerView ()
    {
        val postsCollection = postDao.postCollection
        val query = postsCollection.orderBy("createdAt",com.google.firebase.firestore.Query.Direction.DESCENDING)

        val recyclerViewOptions = FirestoreRecyclerOptions.
        Builder<PostModel>().setQuery(query,PostModel::class.java).build()

        adapter = PostAdapter(recyclerViewOptions,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = MenuInflater(this)
//        inflater.inflate(R.menu.app_bar_menu,menu)
//
//
//
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)

        val logoutItem = menu?.findItem(R.id.logoutButton)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logoutButton -> {
            logOut()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    fun logOut() {
//        Toast.makeText(this,"LoggedOut",Toast.LENGTH_SHORT).show()
//
//
//        val currentUserId = mAuth.currentUser!!.uid
//        val db = FirebaseFirestore.getInstance()
//        db.collection("users").document(currentUserId).delete()
//        Toast.makeText(this,"Deleted user",Toast.LENGTH_SHORT).show()

        mAuth.signOut();

        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent);
    }

}
