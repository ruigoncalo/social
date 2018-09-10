package com.ruigoncalo.social.ui

import android.content.Context
import android.content.Intent
import com.ruigoncalo.social.ui.detail.PostDetailActivity
import javax.inject.Inject

class AppNavigator @Inject constructor(private val context: Context) {

    fun openPostDetail(postId: Int) {
        val intent = PostDetailActivity.buildIntent(context, postId).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

}