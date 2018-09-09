package com.ruigoncalo.social.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ruigoncalo.domain.RetrievePostDetailInteractor
import com.ruigoncalo.social.presentation.model.PostViewEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
        private val interactor: RetrievePostDetailInteractor,
        private val mapper: ViewEntityMapper) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val postsLiveData: MutableLiveData<ViewResource<PostViewEntity>> = MutableLiveData()

    fun getPostsLiveData(): MutableLiveData<ViewResource<PostViewEntity>> {
        return postsLiveData
    }

    fun retrievePost(id: Int) {
        disposables.add(
                interactor.retrievePost(id)
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