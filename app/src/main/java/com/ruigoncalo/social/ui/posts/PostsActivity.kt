package com.ruigoncalo.social.ui.posts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.ruigoncalo.social.R
import com.ruigoncalo.social.injection.ViewModelFactory
import com.ruigoncalo.social.presentation.ViewResource
import com.ruigoncalo.social.presentation.ViewResourceState
import com.ruigoncalo.social.presentation.model.UserPostViewEntity
import com.ruigoncalo.social.presentation.posts.PostsViewModel
import com.ruigoncalo.social.ui.AppNavigator
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_posts.*
import polanski.option.OptionUnsafe
import javax.inject.Inject

class PostsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var appNavigator: AppNavigator

    private lateinit var viewModel: PostsViewModel

    private val postsAdapter by lazy {
        PostsAdapter(this) { postId ->
            appNavigator.openPostDetail(postId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PostsViewModel::class.java)

        setupViews()
        hideLoading()
        hideError()

        observeViewModel()
        viewModel.retrievePosts()
    }

    private fun observeViewModel() {
        viewModel.getPostsLiveData().observe(this,
                Observer<ViewResource<List<UserPostViewEntity>>> { resource ->
                    resource?.let {
                        when (it.state) {
                            ViewResourceState.SUCCESS -> {
                                if (it.data.isSome) {
                                    val viewEntity = OptionUnsafe.getUnsafe(it.data)
                                    showMessages(viewEntity)
                                    hideLoading()
                                    hideError()
                                }
                            }

                            ViewResourceState.ERROR -> {
                                if (it.message.isSome) {
                                    showError(OptionUnsafe.getUnsafe(it.message))
                                    hideLoading()
                                }
                            }

                            ViewResourceState.LOADING -> {
                                showLoading()
                                hideError()
                            }
                        }
                    }
                })
    }

    private fun setupViews() {
        with(postsView) {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun showMessages(posts: List<UserPostViewEntity>) {
        postsAdapter.showPosts(posts)
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun hideError() {

    }
}
