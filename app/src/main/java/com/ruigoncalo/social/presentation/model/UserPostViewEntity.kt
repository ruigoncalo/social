package com.ruigoncalo.social.presentation.model

data class PostViewEntity(val id: Int,
                          val user: UserViewEntity,
                          val title: String,
                          val body: String)