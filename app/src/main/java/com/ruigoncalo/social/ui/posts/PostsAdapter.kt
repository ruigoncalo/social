package com.ruigoncalo.social.ui.posts

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruigoncalo.social.R
import com.ruigoncalo.social.presentation.model.UserPostViewEntity

class PostsAdapter(private val context: Context,
                   private val listener: (Int) -> Unit) : RecyclerView.Adapter<PostItemView>() {

    private val inflater by lazy { LayoutInflater.from(context) }

    private val posts = mutableListOf<UserPostViewEntity>()

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemView {
        val view = inflater.inflate(R.layout.post_item_view, parent, false)
        return PostItemView(view, listener)
    }

    override fun onBindViewHolder(holder: PostItemView, position: Int) {
        holder.bind(posts[position])
    }

    fun showPosts(postsViewEntity: List<UserPostViewEntity>) {
        postsViewEntity.forEach { posts.add(it) }
        notifyDataSetChanged()
    }
}