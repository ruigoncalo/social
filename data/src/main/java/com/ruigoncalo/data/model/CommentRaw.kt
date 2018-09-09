package com.ruigoncalo.data.model

data class CommentRaw(val id: Int,
                      val postId: Int,
                      val name: String,
                      val email: String,
                      val body: String)