package com.ruigoncalo.social.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String, placeholder: Drawable?, error: Drawable?) {
    val request = Picasso.get().load(url)
    placeholder?.let { request.placeholder(it) }
    error?.let { request.error(it) }
    request.into(this)
}