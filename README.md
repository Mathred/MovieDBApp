# MovieDBApp
## Description
Simple Android app demonstating basic interaction with TheMovieDB API.

## Features presented
1. Android clean architecture with MVVM framework.
2. [MovieDbApp](app/src/main/java/com/example/moviedbapp/MovieDbApp.kt) as dependency initializer and injector.
3. Storing and using API keys and signed bundle keys in local files (using [gradle properties](https://github.com/Mathred/MovieDBApp/commit/d0eda9be8aeea53881de9a96307719fdb7569d61#diff-51a0b488f963eb0be6c6599bf5df497313877cf5bdff3950807373912ac1cdc9) and [Manifest placeholders]().
4. LiveData and [generic LoadState](app/src/main/java/com/example/moviedbapp/viewmodel/LoadState.kt) as mediator between data and UI layers.
5. [SingleLiveEvent](app/src/main/java/com/example/moviedbapp/utils/SingleLiveEvent.kt) for one-time LiveData event.
6. ViewBinding and DataBinding.
7. Kotlin coroutines for dispatching jobs.
8. API-calls through Retrofit with OkHttp Interception.
9. GSON for deserializing JSON.
10. Paging3 & Kotlin Flow for handling RecyclerView data (API-calls only).
11. LocalDB (Room) for opened movie details fragments history and local notes for movies.
12. [Push notifications](app/src/main/java/com/example/moviedbapp/services/MovieDbMessagingService.kt) with Pending intents.
13. Permissions handling (via [requestPermissions()](https://github.com/Mathred/MovieDBApp/blob/3842c9b9090629b8f005a2c50ae9a36e6627a347/app/src/main/java/com/example/moviedbapp/ContactsFragment.kt#L55) & [registerForActivityResult(ActivityResultContracts.RequestPermission())](https://github.com/Mathred/MovieDBApp/blob/3842c9b9090629b8f005a2c50ae9a36e6627a347/app/src/main/java/com/example/moviedbapp/LocationFragment.kt#L57))
14. [Contacts import](app/src/main/java/com/example/moviedbapp/ContactsFragment.kt).
15. [Caller & dialer](app/src/main/java/com/example/moviedbapp/utils/Caller.kt).
16. [Location fragment](app/src/main/java/com/example/moviedbapp/LocationFragment.kt) using Google Maps Fragment.
17. Basic [String](app/src/main/java/com/example/moviedbapp/extensions/StringExtensions.kt), [Date](app/src/main/java/com/example/moviedbapp/extensions/DateExtensions.kt), [Dialogs](app/src/main/java/com/example/moviedbapp/utils/Dialogs.kt), Toast, [Data classes](app/src/main/java/com/example/moviedbapp/utils/DataUtils.kt) Extensions.
18. [SoftInput handling](app/src/main/java/com/example/moviedbapp/utils/Input.kt).
19. Saving and using [Shared preferences](app/src/main/java/com/example/moviedbapp/utils/Preferences.kt).
20. Geofence classes (without implementation): [GeofenceBuilder](app/src/main/java/com/example/moviedbapp/utils/GeofenceBuilder.kt), [GeofenceTransitionsJobIntentService](app/src/main/java/com/example/moviedbapp/services/GeofenceTransitionsJobIntentService.kt), [GeofenceErrorMessages ](app/src/main/java/com/example/moviedbapp/services/GeofenceErrorMessages.kt), [GeofenceBroadcastReceiver](app/src/main/java/com/example/moviedbapp/services/GeofenceBroadcastReceiver.kt).
21. Navigation drawer with TopAppBar.
22. Navigation via [Navigator](app/src/main/java/com/example/moviedbapp/utils/Navigator.kt) class with Activity & Fragment extensions.
23. Lazy properties.
24. Vertical [RecyclerView](app/src/main/java/com/example/moviedbapp/ui/rvadapter/MovieCategoryAdapter.kt) with nested horizontal RecyclerView.
25. [RecyclerView](app/src/main/java/com/example/moviedbapp/ui/rvadapter/MovieListAdapter.kt) with different view types.
26. Different buildTypes and flavors (just for demo).

## APIs
1. [TheMovieDB API](https://developers.themoviedb.org/3/getting-started/introduction)
2. [Google Maps API](https://developers.google.com/maps/documentation/android-sdk/overview)

## Internal libraries
1. kotlin-kapt
2. kotlin-parcelize
3. Room
4. Paging3
5. Firebase

## External libraries
1. [GSON](https://github.com/google/gson)
2. [Retrofit](https://square.github.io/retrofit/)
3. [OkHttp logging interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
4. [Coil](https://coil-kt.github.io/coil/)
