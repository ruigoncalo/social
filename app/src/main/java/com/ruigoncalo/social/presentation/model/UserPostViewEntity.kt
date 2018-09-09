package com.ruigoncalo.social.presentation.model

data class UserPostViewEntity(val postId: Int,
                              val user: UserViewEntity,
                              val title: String,
                              val body: String)