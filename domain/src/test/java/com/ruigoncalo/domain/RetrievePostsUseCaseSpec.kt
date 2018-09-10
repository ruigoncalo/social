package com.ruigoncalo.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.ruigoncalo.domain.model.Post
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import polanski.option.Option

class RetrievePostsUseCaseSpec : Spek({

    val repository: Repository<Int, Post> = mock()
    val tested = RetrievePostsUseCase(repository)
    var observerPosts = TestObserver<List<Post>>()
    var observerPost = TestObserver<Post>()

    val mockKey = 1
    val mockPost = Post(mockKey, 1, "title", "body")
    val mockPosts = listOf(mockPost)

    beforeEachTest {
        reset(repository)

        observerPosts = TestObserver()
        observerPost = TestObserver()
    }

    describe("retrieve a list of posts") {

        context("no posts to get") {
            beforeEachTest {
                whenever(repository.getAll()).thenReturn(Observable.just(Option.none()))
                whenever(repository.fetchAll()).thenReturn(Completable.complete())
                tested.retrievePosts().subscribe(observerPosts)
            }

            it("should fetch posts from repo") {
                verify(repository).fetchAll()
            }

            it("should return nothing and complete") {
                with(observerPosts) {
                    assertNoValues()
                    assertNoErrors()
                    assertComplete()
                }
            }
        }

        context("there are posts") {
            beforeEachTest {
                whenever(repository.getAll()).thenReturn(Observable.just(Option.ofObj(mockPosts)))
                tested.retrievePosts().subscribe(observerPosts)
            }

            it("should not fetch posts from repo") {
                verify(repository, never()).fetchAll()
            }

            it("should return value and complete") {
                with(observerPosts) {
                    assertValue(mockPosts)
                    assertNoErrors()
                    assertComplete()
                }
            }
        }

        context("error fetching posts") {
            val error = Throwable()
            beforeEachTest {
                whenever(repository.getAll()).thenReturn(Observable.just(Option.none()))
                whenever(repository.fetchAll()).thenReturn(Completable.error(error))
                tested.retrievePosts().subscribe(observerPosts)
            }

            it("should return error") {
                observerPosts.assertError(error)
            }
        }
    }

    describe("retrieve a post") {

        context("no post to get") {
            beforeEachTest {
                whenever(repository.getSingular(mockKey)).thenReturn(Observable.just(Option.none()))
                whenever(repository.fetchSingular(mockKey)).thenReturn(Completable.complete())
                tested.retrievePost(mockKey).subscribe(observerPost)
            }

            it("should fetch post from repo") {
                verify(repository).fetchSingular(mockKey)
            }

            it("should return nothing and complete") {
                with(observerPost) {
                    assertNoValues()
                    assertNoErrors()
                    assertComplete()
                }
            }
        }

        context("there is a post") {
            beforeEachTest {
                whenever(repository.getSingular(mockKey)).thenReturn(Observable.just(Option.ofObj(mockPost)))
                tested.retrievePost(mockKey).subscribe(observerPost)
            }

            it("should not fetch the post from repo") {
                verify(repository, never()).fetchSingular(mockKey)
            }

            it("should return post and complete") {
                with(observerPost) {
                    assertValue(mockPost)
                    assertNoErrors()
                    assertComplete()
                }
            }
        }

        context("error fetching post") {
            val error = Throwable()
            beforeEachTest {
                whenever(repository.getAll()).thenReturn(Observable.just(Option.none()))
                whenever(repository.fetchAll()).thenReturn(Completable.error(error))
                tested.retrievePosts().subscribe(observerPosts)
            }

            it("should return error") {
                observerPosts.assertError(error)
            }
        }
    }

})