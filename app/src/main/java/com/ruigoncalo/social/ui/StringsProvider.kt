package com.ruigoncalo.social.ui

import android.content.Context
import com.ruigoncalo.social.R
import javax.inject.Inject

class StringsProvider @Inject constructor(private val context: Context) {

    fun genericErrorMessage(): String {
        return context.getString(R.string.generic_error)
    }

    fun commentLabel(): String {
        return context.getString(R.string.comments)
    }

}