package com.example.hivroms

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import android.widget.Toast
    import com.example.hivroms.daos.PostDao
    import kotlinx.android.synthetic.main.activity_create_post.*

    class CreatePostActivity : AppCompatActivity() {

        private lateinit var postDao: PostDao
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_create_post)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            postDao = PostDao()
            postButton.setOnClickListener {
                val textInput = postInput.text.toString().trim()

                if(textInput.isNotEmpty())
                {
                    Toast.makeText(this,textInput, Toast.LENGTH_LONG).show()
                    postDao.addPost(textInput)
                    finish()
                }

            }
        }
    }

//package com.example.socialapp

//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.example.socialapp.daos.PostDao
//import kotlinx.android.synthetic.main.activity_create_post.*

//class CreatePostActivity : AppCompatActivity() {
//
//    private lateinit var postDao: PostDao
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_post)
//
//        postDao = PostDao()
//
//        postButton.setOnClickListener {
//            val input = postInput.text.toString().trim()
//            if(input.isNotEmpty()) {
//                postDao.addPost(input)
//                finish()
//            }
//        }
//
//        //setUpRecyclerView()
//    }
//
////    private fun setUpRecyclerView() {
////
////    }
//}