package com.ruigoncalo.domain

import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.User
import com.ruigoncalo.domain.model.UserPost
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class RetrieveUsersPostsUseCase @Inject constructor(
        private val retrievePostsUseCase: RetrievePostsUseCase,
        private val retrieveUserUseCase: RetrieveUserUseCase,
        private val mapper: UserPostMapper) : RetrieveUsersPostsInteractor {

    override fun retrieveUsersPosts(): Observable<List<UserPost>> {
        return retrievePostsUseCase.retrievePosts()
                .zipWith(retrieveUserUseCase.retrieveUsers(),
                        BiFunction<List<Post>, List<User>, List<UserPost>> { posts, users -> mapper.reduce(posts, users) })
    }

    override fun retrieveUserPost(key: Int): Observable<UserPost> {
        return retrievePostsUseCase.retrievePost(key)
                .flatMap { post ->
                    retrieveUserUseCase.retrieveUser(post.userId)
                            .map { mapper.map(it, post) }
                }
    }
}