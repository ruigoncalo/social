package com.ruigoncalo.social.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruigoncalo.social.R
import com.ruigoncalo.social.presentation.model.PostViewEntity
import com.ruigoncalo.social.presentation.model.PostsViewEntity

class PostsAdapter(private val context: Context) : RecyclerView.Adapter<PostItemView>() {

    private val inflater by lazy { LayoutInflater.from(context) }

    private val posts = mutableListOf<PostViewEntity>()

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemView {
        val view = inflater.inflate(R.layout.post_item_view, parent, false)
        return PostItemView(view)
    }

    override fun onBindViewHolder(holder: PostItemView, position: Int) {
        holder.bind(posts[position])
    }

    fun showPosts(postsViewEntity: PostsViewEntity) {
        postsViewEntity.posts.forEach { posts.add(it) }
        notifyDataSetChanged()
    }
}