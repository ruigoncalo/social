package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.User
import com.ruigoncalo.domain.model.UserPost
import javax.inject.Inject

class UserPostMapper @Inject constructor() {

    fun reduce(users: List<User>, posts: List<Post>): List<UserPost> {
        return posts.mapNotNull { post ->
            val user = users.firstOrNull { post.userId == it.id }
            user?.let {
                UserPost(post.id, it, post.title, post.body)
            }
        }
    }

    fun reduce(user: User, post: Post): UserPost {
        return UserPost(post.id, user, post.title, post.body)
    }
}