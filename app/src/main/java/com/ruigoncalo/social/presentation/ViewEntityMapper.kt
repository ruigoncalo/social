package com.ruigoncalo.social.presentation

import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.User
import com.ruigoncalo.social.presentation.model.PostViewEntity
import com.ruigoncalo.social.presentation.model.UserViewEntity
import javax.inject.Inject

class ViewEntityMapper @Inject constructor(private val avatarUrlGenerator: AvatarUrlGenerator) {

    fun map(model: List<Post>): List<PostViewEntity> {
        return model.map { it.toViewEntity(avatarUrlGenerator) }
    }
}

fun Post.toViewEntity(avatarUrlGenerator: AvatarUrlGenerator): PostViewEntity {
    return PostViewEntity(this.id, this.user.toViewEntity(avatarUrlGenerator), this.title, this.body)
}

fun User.toViewEntity(avatarUrlGenerator: AvatarUrlGenerator): UserViewEntity {
    return UserViewEntity(this.id, this.name, avatarUrlGenerator.generateAvatar(this.id))
}