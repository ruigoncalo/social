package com.ruigoncalo.data

import com.ruigoncalo.data.model.PostRaw
import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.Posts
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun map(rawList: List<PostRaw>): Posts {
        val posts = rawList.map { it.toModel() }
        return Posts(posts)
    }
}

fun PostRaw.toModel(): Post {
    return Post(this.id, this.userId, this.title, this.body)
}