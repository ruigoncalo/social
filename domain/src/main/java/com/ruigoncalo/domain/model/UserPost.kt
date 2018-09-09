package com.ruigoncalo.domain.model

data class UserPost(val postId: Int,
                    val user: User,
                    val title: String,
                    val body: String)