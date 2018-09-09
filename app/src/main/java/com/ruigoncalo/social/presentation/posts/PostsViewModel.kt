package com.ruigoncalo.social.presentation.posts

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ruigoncalo.domain.RetrieveUsersPostsInteractor
import com.ruigoncalo.social.presentation.ViewEntityMapper
import com.ruigoncalo.social.presentation.ViewResource
import com.ruigoncalo.social.presentation.model.UserPostViewEntity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
        private val interactor: RetrieveUsersPostsInteractor,
        private val mapper: ViewEntityMapper) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val postsLiveData: MutableLiveData<ViewResource<List<UserPostViewEntity>>> = MutableLiveData()

    fun getPostsLiveData(): MutableLiveData<ViewResource<List<UserPostViewEntity>>> {
        return postsLiveData
    }

    fun retrievePosts() {
        disposables.add(
                interactor.retrieveUsersPosts()
                        .doOnNext { postsLiveData.postValue(ViewResource.loading()) }
                        .observeOn(Schedulers.computation())
                        .flatMapSingle { posts ->
                            Observable.fromIterable(posts)
                                    .map { mapper.map(it) }
                                    .toList()
                        }
                        .subscribeOn(Schedulers.io())
                        .subscribe({ userPosts ->
                            postsLiveData.postValue(ViewResource.success(userPosts))
                        }, { e ->
                            postsLiveData.postValue(ViewResource.error(e.message))
                        })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}