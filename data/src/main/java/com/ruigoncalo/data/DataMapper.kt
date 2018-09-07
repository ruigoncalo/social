package com.ruigoncalo.data

import com.ruigoncalo.data.model.PostRaw
import com.ruigoncalo.data.model.UserRaw
import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.User
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun map(postRawList: List<PostRaw>, userRawList: List<UserRaw>): List<Post> {
        return postRawList.mapNotNull { postRaw ->
            val user = userRawList.firstOrNull { it.id == postRaw.userId }
            postRaw.toModel(user)
        }
    }
}

fun PostRaw.toModel(userRaw: UserRaw?): Post? {
    return userRaw?.let {
        Post(this.id, it.toModel(), this.title, this.body)
    }
}

fun UserRaw.toModel(): User {
    return User(this.id, this.name, this.username, this.email, this.phone, this.website)
}