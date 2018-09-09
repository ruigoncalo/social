package com.ruigoncalo.social.presentation

import com.ruigoncalo.domain.model.Comment
import com.ruigoncalo.domain.model.User
import com.ruigoncalo.domain.model.UserPost
import com.ruigoncalo.social.presentation.model.CommentViewEntity
import com.ruigoncalo.social.presentation.model.PostCommentsViewEntity
import com.ruigoncalo.social.presentation.model.UserPostViewEntity
import com.ruigoncalo.social.presentation.model.UserViewEntity
import javax.inject.Inject

class ViewEntityMapper @Inject constructor(private val avatarUrlGenerator: AvatarUrlGenerator) {

    fun map(model: UserPost): UserPostViewEntity {
        return UserPostViewEntity(model.postId, map(model.user), model.title, model.body)
    }

    private fun map(model: User): UserViewEntity {
        return UserViewEntity(model.id, model.name, avatarUrlGenerator.generateAvatar(model.id))
    }

    fun map(model: Comment): CommentViewEntity {
        return CommentViewEntity(model.id, model.postId, model.name, model.email, model.body)
    }

    fun map(modelList: List<Comment>): PostCommentsViewEntity {
        return PostCommentsViewEntity(modelList.size.toString())
    }
}