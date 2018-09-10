package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.User
import com.ruigoncalo.domain.model.UserPost
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class RetrieveUsersPostsUseCase @Inject constructor(
        private val retrievePostsUseCase: RetrievePostsUseCase,
        private val retrieveUsersUseCase: RetrieveUsersUseCase,
        private val mapper: UserPostMapper) : RetrieveUsersPostsInteractor {

    override fun retrieveUsersPosts(): Observable<List<UserPost>> {
        return retrievePostsUseCase.retrievePosts()
                .zipWith(retrieveUsersUseCase.retrieveUsers(),
                        BiFunction<List<Post>, List<User>, List<UserPost>> { posts, users -> mapper.reduce(users, posts) })
    }

    override fun retrieveUserPost(key: Int): Observable<UserPost> {
        return retrievePostsUseCase.retrievePost(key)
                .flatMap { post ->
                    retrieveUsersUseCase.retrieveUser(post.userId)
                            .map { mapper.reduce(it, post) }
                }
    }
}