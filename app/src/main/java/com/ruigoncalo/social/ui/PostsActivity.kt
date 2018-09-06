package com.ruigoncalo.social.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.ruigoncalo.social.R
import com.ruigoncalo.social.injection.ViewModelFactory
import com.ruigoncalo.social.presentation.PostsViewModel
import com.ruigoncalo.social.presentation.ViewResource
import com.ruigoncalo.social.presentation.ViewResourceState
import com.ruigoncalo.social.presentation.model.PostsViewEntity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_posts.*
import polanski.option.OptionUnsafe
import javax.inject.Inject

class PostsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: PostsViewModel

    private val postsAdapter by lazy { PostsAdapter(this) }

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
    }

    private fun observeViewModel() {
        viewModel.getPostsLiveData().observe(this,
                Observer<ViewResource<PostsViewEntity>> { resource ->
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
        }
    }

    private fun showMessages(posts: PostsViewEntity) {
        postsAdapter.showPosts(posts)
    }

    private fun showLoading() {
        // empty
    }

    private fun hideLoading() {
        // empty
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun hideError() {
        // empty
    }
}
