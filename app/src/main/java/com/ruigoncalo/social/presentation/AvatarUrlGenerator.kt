package com.ruigoncalo.social.presentation

import javax.inject.Inject

class AvatarUrlGenerator @Inject constructor() {

    fun generateAvatar(id: Int): String {
        return "https://api.adorable.io/avatars/$id"
    }
}