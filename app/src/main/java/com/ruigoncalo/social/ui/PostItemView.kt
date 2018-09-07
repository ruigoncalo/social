package com.ruigoncalo.social.ui

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ruigoncalo.social.R
import com.ruigoncalo.social.presentation.model.PostViewEntity

class PostItemView(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(postViewEntity: PostViewEntity) {
        val titleView = view.findViewById<TextView>(R.id.postTitleText)
        val bodyView = view.findViewById<TextView>(R.id.postBodyText)
        val imageView = view.findViewById<ImageView>(R.id.userImageView)
        val userNameView = view.findViewById<TextView>(R.id.userNameText)

        imageView.loadImage(postViewEntity.user.avatarId,
                ContextCompat.getDrawable(view.context, R.drawable.ic_launcher_background),
                ContextCompat.getDrawable(view.context, R.drawable.ic_face))
        userNameView.text = postViewEntity.user.name
        titleView.text = postViewEntity.title
        bodyView.text = postViewEntity.body
    }
}