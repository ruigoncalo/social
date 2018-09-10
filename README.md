# Social posts app

A simple app that shows a list of posts and each post detail.

----
## Modules
The app's source code comprises three main modules:

* `app` (Android module)

* `domain` (Kotlin module)

* `data` (Kotlin moduleª)

ª - For simplicity, in this app the persistence layer is just memory, but in case it was a database or files (ex: `SharedPreferences`) the `cache` directory within the `data` module should be a separate module itself (dependent on Android libs).

## `domain` module

Defines the app's main actions and object entities. This app have two main actions: *retrieve users' posts* and *retrieve comments*.

Therefore, two interactors were created to execute each action: `RetrieveUsersPostsInteractor` and `RetrievePostCommentsInteractor`. Only these interactors are defined through an interface, because they will be called on the `app` module. This module will depend on abstractions and not implementations.

The `RetrieveUsersPostsUseCase` that implements the `RetrieveUsersPostsInteractor` depends on use cases that will get the `User` objects and `Post` objects from each repository, separately. Later, both sets of data are merged using a reduce method from the `UserPostMapper`, creating the list of `UserPost`.

The main object entities are `UserPost` that represents a `Post` with the corresponding `User`, and `Comment` that defines a post comment.

At last, there are the `Repository` and `KeyBasedRepository` interfaces that bridge the `domain` and `data` modules. These interfaces establish a contract that the `data` module must implement.

Note: For simplicity, the `KeyBasedRepository` was created to support repositories that only get/fetch data using param keys, but it could be avoided.

## `data` module

Aims at fetching and storing data both remotely and locally.  The `/remote` directory holds all classes that take care of fetching data remotely. Similarly, the `/cache` directory holds all classes that allow getting and storing data locally. For simplicity, this app uses only memory to cache data.

This module provides a `ReactiveStore` that is built on top of the cache layer to establish a *reactive* behavior when objects are stored locally. This means that when an object is stored through the `store` method, the same object is also emitted on the `get` method.

As mention above, the `Repository` is the only interface the `data` module must use to exchange data. Therefore, there are three repositories, `PostsRepository`, `UsersRepository` and `CommentsRepository` that are used on the `domain` layer. The name is self explanatory of what type of data each repository deals with.

## `app` module

Contains a `/presentation` directory that comprises the view entities and view model classes that define the user interface, following the *MVVM pattern*. The app displays two screens - `PostsActivity` and `PostDetailsActivity` - each one with a corresponding view model -`PostsViewModel` and `PostDetailViewModel`.

The `PostsViewModel` depends on the `RetrieveUsersPostsInteractor` to get the desired data. So, it calls the interactor to get the data and posts it through a `LiveData` object. In case something goes wrong, an error message is also posted.

The `PostDetailViewModel` depends on both the `RetrieveUsersPostsInteractor` and `RetrievePostCommentsInteractor`, because along with the `UserPost` data, it also needs to show the number of comments. Using this approach, with two interactors, makes it easier to refactor and allows to reusing those interactors in different parts of the app, if needed.

At last, the `PostsActivity` and `PostDetailActivity` observe the view model and renders the view according to its values.

## Build and run

1. Create a `local.properties` with `sdk.dir=<path_to_android_sdk>`and add to the root of the project

2. Run `./gradlew app:assemble` to generate the apk

### Tests

The domain module have some tests (using the [Spek framework](https://spekframework.org/))

Run `./gradlew domain:test` to run the tests