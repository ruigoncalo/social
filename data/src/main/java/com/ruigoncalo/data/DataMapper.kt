package com.ruigoncalo.data

import com.ruigoncalo.data.model.CommentRaw
import com.ruigoncalo.data.model.PostRaw
import com.ruigoncalo.data.model.UserRaw
import com.ruigoncalo.domain.model.Comment
import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.User
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun map(raw: UserRaw): User {
        return User(raw.id, raw.name, raw.username, raw.email, raw.phone, raw.website)
    }

    fun map(raw: PostRaw): Post {
        return Post(raw.id, raw.userId, raw.title, raw.body)
    }

    fun map(raw: CommentRaw): Comment {
        return Comment(raw.id, raw.postId, raw.name, raw.email, raw.body)
    }
}