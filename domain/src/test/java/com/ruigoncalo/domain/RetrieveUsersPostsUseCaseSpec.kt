package com.ruigoncalo.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import com.ruigoncalo.domain.model.Post
import com.ruigoncalo.domain.model.User
import com.ruigoncalo.domain.model.UserPost
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class RetrieveUsersPostsUseCaseSpec : Spek({

    val retrievePostsUseCase: RetrievePostsUseCase = mock()
    val retrieveUsersUseCase: RetrieveUsersUseCase = mock()
    val mapper: UserPostMapper = mock()
    val tested = RetrieveUsersPostsUseCase(retrievePostsUseCase, retrieveUsersUseCase, mapper)
    var observerUsersPosts = TestObserver<List<UserPost>>()
    var observerUserPost = TestObserver<UserPost>()

    val mockPostKey = 1
    val mockUserKey = 2
    val mockUser = User(mockUserKey, "name", "username", "email", "phone", "website")
    val mockUserList = listOf(mockUser)
    val mockPost = Post(mockPostKey, mockUser.id, "title", "body")
    val mockPostList = listOf(mockPost)
    val mockUserPost = UserPost(mockPost.id, mockUser, mockPost.title, mockPost.body)
    val mockUserPostList = listOf(mockUserPost)

    beforeEachTest {
        reset(retrievePostsUseCase)
        reset(retrieveUsersUseCase)
        reset(mapper)

        whenever(mapper.reduce(mockUser, mockPost)).thenReturn(mockUserPost)
        whenever(mapper.reduce(mockUserList, mockPostList)).thenReturn(mockUserPostList)
        observerUsersPosts = TestObserver()
        observerUserPost = TestObserver()
    }

    describe("retrieve UserPosts") {

        context("there are users and posts") {
            beforeEachTest {
                whenever(retrieveUsersUseCase.retrieveUsers()).thenReturn(Observable.just(mockUserList))
                whenever(retrievePostsUseCase.retrievePosts()).thenReturn(Observable.just(mockPostList))
                tested.retrieveUsersPosts().subscribe(observerUsersPosts)
            }

            it("should return the list of UserPost") {
                with(observerUsersPosts) {
                    assertValue(mockUserPostList)
                    assertNoErrors()
                    assertComplete()
                }
            }
        }

        context("there is an error") {
            val error = Throwable()
            beforeEachTest {
                whenever(retrieveUsersUseCase.retrieveUsers()).thenReturn(Observable.error(error))
                whenever(retrievePostsUseCase.retrievePosts()).thenReturn(Observable.just(mockPostList))
                tested.retrieveUsersPosts().subscribe(observerUsersPosts)
            }

            it("should return error") {
                observerUsersPosts.assertError(error)
            }
        }

        context("there are no users") {
            beforeEachTest {
                whenever(retrieveUsersUseCase.retrieveUsers()).thenReturn(Observable.just(listOf()))
                whenever(retrievePostsUseCase.retrievePosts()).thenReturn(Observable.just(mockPostList))
                whenever(mapper.reduce(listOf(), mockPostList)).thenReturn(listOf())
                tested.retrieveUsersPosts().subscribe(observerUsersPosts)
            }

            it("should return empty list") {
                with(observerUsersPosts) {
                    assertValue(listOf())
                    assertNoErrors()
                    assertComplete()
                }
            }
        }
    }

    describe("retrieve a single UserPosts") {

        context("there is a user and a post") {
            beforeEachTest {
                whenever(retrieveUsersUseCase.retrieveUser(mockUserKey)).thenReturn(Observable.just(mockUser))
                whenever(retrievePostsUseCase.retrievePost(mockPostKey)).thenReturn(Observable.just(mockPost))
                tested.retrieveUserPost(mockPostKey).subscribe(observerUserPost)
            }

            it("should return the UserPost") {
                with(observerUserPost) {
                    assertValue(mockUserPost)
                    assertNoErrors()
                    assertComplete()
                }
            }
        }

    }

})