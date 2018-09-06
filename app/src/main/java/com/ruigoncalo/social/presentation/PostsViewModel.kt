package com.ruigoncalo.social.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ruigoncalo.domain.RetrievePostsInteractor
import com.ruigoncalo.social.presentation.model.PostsViewEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
        private val interactor: RetrievePostsInteractor,
        private val mapper: ViewEntityMapper) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val postsLiveData: MutableLiveData<ViewResource<PostsViewEntity>> = MutableLiveData()

    init {
        retrievePosts()
    }

    fun getPostsLiveData(): MutableLiveData<ViewResource<PostsViewEntity>> {
        return postsLiveData
    }

    private fun retrievePosts() {
        disposables.add(
                interactor.retrievePosts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext { postsLiveData.value = ViewResource.loading() }
                        .observeOn(Schedulers.computation())
                        .map { mapper.map(it) }
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            postsLiveData.postValue(ViewResource.success(it))
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