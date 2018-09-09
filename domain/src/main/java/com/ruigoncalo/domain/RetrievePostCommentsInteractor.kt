package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Comment
import io.reactivex.Observable

interface RetrievePostCommentsInteractor {

    fun retrievePostComments(key: Int): Observable<List<Comment>>
}