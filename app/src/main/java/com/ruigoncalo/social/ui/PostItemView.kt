package com.ruigoncalo.social.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ruigoncalo.social.R
import com.ruigoncalo.social.presentation.model.PostViewEntity

class PostItemView(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(postViewEntity: PostViewEntity) {
        val titleView = view.findViewById<TextView>(R.id.postTitleText)
        val bodyView = view.findViewById<TextView>(R.id.postBodyText)

        titleView.text = postViewEntity.title
        bodyView.text = postViewEntity.body
    }
}