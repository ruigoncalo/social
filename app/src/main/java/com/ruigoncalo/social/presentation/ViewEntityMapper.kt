package com.ruigoncalo.social.presentation

import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.Posts
import com.ruigoncalo.social.presentation.model.PostViewEntity
import com.ruigoncalo.social.presentation.model.PostsViewEntity
import javax.inject.Inject

class ViewEntityMapper @Inject constructor() {

    fun map(model: Posts): PostsViewEntity {
        val posts = model.posts.map { it.toViewEntity() }
        return PostsViewEntity(posts)
    }
}

fun Post.toViewEntity(): PostViewEntity {
    return PostViewEntity(this.id, this.title, this.body)
}