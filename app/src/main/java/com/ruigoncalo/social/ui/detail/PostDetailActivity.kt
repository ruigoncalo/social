package com.ruigoncalo.social.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ruigoncalo.social.R
import com.ruigoncalo.social.injection.ViewModelFactory
import com.ruigoncalo.social.presentation.ViewResource
import com.ruigoncalo.social.presentation.ViewResourceState
import com.ruigoncalo.social.presentation.detail.PostDetailViewModel
import com.ruigoncalo.social.presentation.model.PostCommentsViewEntity
import com.ruigoncalo.social.presentation.model.UserPostViewEntity
import com.ruigoncalo.social.ui.UiConstants.Companion.INVALID_POST_ID
import com.ruigoncalo.social.ui.UiConstants.Companion.POST_ID_EXTRA
import com.ruigoncalo.social.ui.loadImage
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_post_detail.*
import polanski.option.OptionUnsafe
import javax.inject.Inject

class PostDetailActivity : AppCompatActivity() {

    companion object {

        fun buildIntent(context: Context, postId: Int): Intent {
            return Intent(context, PostDetailActivity::class.java).apply {
                putExtra(POST_ID_EXTRA, postId)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: PostDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PostDetailViewModel::class.java)

        setupViews()
        hideLoading()
        hideError()

        observeUserPostViewModel()
        observeCommentsViewModel()

        val postId = intent.getIntExtra(POST_ID_EXTRA, INVALID_POST_ID)
        viewModel.retrievePost(postId)
        viewModel.retrievePostComments(postId)
    }

    private fun observeUserPostViewModel() {
        viewModel.getUserPostLiveData().observe(this,
                Observer<ViewResource<UserPostViewEntity>> { resource ->
                    resource?.let {
                        when (it.state) {
                            ViewResourceState.SUCCESS -> {
                                if (it.data.isSome) {
                                    val viewEntity = OptionUnsafe.getUnsafe(it.data)
                                    showPostDetail(viewEntity)
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

    private fun observeCommentsViewModel() {
        viewModel.getPostCommentsLiveData().observe(this,
                Observer<ViewResource<PostCommentsViewEntity>> { resource ->
                    resource?.let {
                        when (it.state) {
                            ViewResourceState.SUCCESS -> {
                                if (it.data.isSome) {
                                    val viewEntity = OptionUnsafe.getUnsafe(it.data)
                                    showComments(viewEntity)
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

    }

    private fun showPostDetail(post: UserPostViewEntity) {
        userImageView.loadImage(post.user.avatarUrl,
                ContextCompat.getDrawable(this, R.drawable.ic_launcher_background),
                ContextCompat.getDrawable(this, R.drawable.ic_face))

        userNameText.text = post.user.name
        postTitleText.text = post.title
        postBodyText.text = post.body
    }

    private fun showComments(postComments: PostCommentsViewEntity) {
        commentsText.text = postComments.commentsText
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