package com.ruigoncalo.social.ui.posts

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ruigoncalo.social.R
import com.ruigoncalo.social.presentation.model.UserPostViewEntity
import com.ruigoncalo.social.ui.loadImage

class PostItemView(private val view: View,
                   private val listener: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(postViewEntity: UserPostViewEntity) {
        val titleView = view.findViewById<TextView>(R.id.postTitleText)
        val imageView = view.findViewById<ImageView>(R.id.userImageView)
        val userNameView = view.findViewById<TextView>(R.id.userNameText)

        imageView.loadImage(postViewEntity.user.avatarUrl,
                ContextCompat.getDrawable(view.context, R.drawable.ic_launcher_background),
                ContextCompat.getDrawable(view.context, R.drawable.ic_face))

        userNameView.text = postViewEntity.user.name
        titleView.text = postViewEntity.title

        view.setOnClickListener {
            listener.invoke(postViewEntity.postId)
        }
    }
}