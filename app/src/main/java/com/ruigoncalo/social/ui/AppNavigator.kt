package com.ruigoncalo.social.ui

import android.content.Context
import com.ruigoncalo.social.ui.detail.PostDetailActivity
import javax.inject.Inject

class AppNavigator @Inject constructor(private val context: Context) {

    fun openPostDetail(postId: Int) {
        val intent = PostDetailActivity.buildIntent(context, postId)
        context.startActivity(intent)
    }

}