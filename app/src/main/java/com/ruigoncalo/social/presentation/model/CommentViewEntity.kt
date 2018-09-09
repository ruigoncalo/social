package com.ruigoncalo.social.presentation.model

data class CommentViewEntity(val id: Int,
                             val postId: Int,
                             val name: String,
                             val email: String,
                             val body: String)