package com.example.hivroms.models

class PostModel(
    val text:String = "",
    val createdBy: UserModel = UserModel(),
    val createdAt: Long =0L,
    val likedBy: ArrayList<String> = ArrayList()
)
