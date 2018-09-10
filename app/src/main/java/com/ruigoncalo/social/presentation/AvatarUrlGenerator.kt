package com.ruigoncalo.social.presentation

import javax.inject.Inject

class AvatarUrlGenerator @Inject constructor() {

    companion object {
        private const val AVATAR_SOURCE = "https://api.adorable.io/avatars"
    }

    fun generateAvatar(id: Int): String {
        return "$AVATAR_SOURCE/$id"
    }
}