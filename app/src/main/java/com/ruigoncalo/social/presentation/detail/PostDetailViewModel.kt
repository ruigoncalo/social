package com.ruigoncalo.social.presentation.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ruigoncalo.domain.RetrievePostCommentsInteractor
import com.ruigoncalo.domain.RetrieveUsersPostsInteractor
import com.ruigoncalo.social.presentation.ViewEntityMapper
import com.ruigoncalo.social.presentation.ViewResource
import com.ruigoncalo.social.presentation.model.PostCommentsViewEntity
import com.ruigoncalo.social.presentation.model.UserPostViewEntity
import com.ruigoncalo.social.ui.StringsProvider
import com.ruigoncalo.social.ui.UiConstants.Companion.INVALID_POST_ID
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
        private val userPostInteractor: RetrieveUsersPostsInteractor,
        private val commentsInteractor: RetrievePostCommentsInteractor,
        private val mapper: ViewEntityMapper,
        private val stringsProvider: StringsProvider) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val userPostLiveData: MutableLiveData<ViewResource<UserPostViewEntity>> = MutableLiveData()
    private val postCommentsLiveData: MutableLiveData<ViewResource<PostCommentsViewEntity>> = MutableLiveData()

    fun getUserPostLiveData(): MutableLiveData<ViewResource<UserPostViewEntity>> {
        return userPostLiveData
    }

    fun getPostCommentsLiveData(): MutableLiveData<ViewResource<PostCommentsViewEntity>> {
        return postCommentsLiveData
    }

    fun retrievePost(id: Int) {
        if (id == INVALID_POST_ID) {
            userPostLiveData.postValue(ViewResource.error(stringsProvider.genericErrorMessage()))
        } else {
            disposables.add(
                    userPostInteractor.retrieveUserPost(id)
                            .doOnNext { userPostLiveData.postValue(ViewResource.loading()) }
                            .observeOn(Schedulers.computation())
                            .map { mapper.map(it) }
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                userPostLiveData.postValue(ViewResource.success(it))
                            }, { e ->
                                userPostLiveData.postValue(ViewResource.error(e.message))
                            })
            )
        }
    }

    fun retrievePostComments(id: Int) {
        if (id == INVALID_POST_ID) {
            userPostLiveData.postValue(ViewResource.error(stringsProvider.genericErrorMessage()))
        } else {
            disposables.add(
                    commentsInteractor.retrievePostComments(id)
                            .doOnNext { postCommentsLiveData.postValue(ViewResource.loading()) }
                            .observeOn(Schedulers.computation())
                            .map { mapper.map(it) }
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                postCommentsLiveData.postValue(ViewResource.success(it))
                            }, { e ->
                                postCommentsLiveData.postValue(ViewResource.error(e.message))
                            })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}