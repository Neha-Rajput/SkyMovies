# Sky Movie List 

## Introduction

Sky Movie is an application in which i am showing list of movies which contain images and title & genere. also a search functionality user can search movies by "title" or "Genere" and fetch data from list.
* Fetching Data from Network API. Calling API using Retrofit.
* Showing result in recyclerview type of gridview.
* Search view for serching and filtering movies.
* following MVVM design pettern.
* Using Data binding and View binding.




## Tools 

* MAC book pro 15
* Android Studio (version 4.2).
* If kotlin plugin is not installed in Android Studio.
* install Kotlin plugin in Android Studio.

 File-> Setting-> plugin -> MarketPlace -> type kotlin in search bar -> Install -> Apply-> ok.
 
* Minimum SDK version 16.
* Compile SDK version 29.
* Testing Mobile Device (Android version 10).

* Design pettern : MVVM
*The separation of the code in MVVM is divided into View, ViewModel and Model:

*View is the collection of visible elements, which also receives user input. This includes user interfaces (UI), animations and text. The content of View is not interacted with directly to change what is presented.

*ViewModel is located between the View and Model layers. This is where the controls for interacting with View are housed, while binding is used to connect the UI elements in View to the controls in ViewModel.

*Model houses the logic for the program, which is retrieved by the ViewModel upon its own receipt of input from the user through View.

## Declaring dependencies

## Glide 
A powerful image downloading and caching library for Android.

```
implementation 'com.github.bumptech.glide:glide:version'
```

## RecyclerView
RecyclerView makes it easy to efficiently display large sets of data. We can perform many operation in recyclerview.

```
implementation 'androidx.recyclerview:recyclerview:version'
```

## Retrofit2
Retrofit is used for calling networking API through request URL and getting response. 

```
   implementation 'com.squareup.retrofit2:retrofit:version'
    implementation 'com.squareup.retrofit2:converter-gson:version'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:version'

```

## RXJava
RxJava is a Java VM implementation of ReactiveX a library for composing asynchronous and event-based programs by using observable sequences. The building blocks of RxJava are Observables and Subscribers. Observable is used for emitting items and Subscriber is used for consuming those items.

```
implementation 'io.reactivex.rxjava2:rxandroid:version'
implementation 'io.reactivex.rxjava2:rxjava:version'
```


## JUnit & Espresso
JUnit and Espresso are used for unit testing and instrumental testing. By default, these libraries are implemented in the Android build.gradle file.

``` 
    testImplementation 'junit:junit:version'
    androidTestImplementation 'androidx.test.ext:junit:version'
    androidTestImplementation 'androidx.test.espresso:espresso-core:version'

    androidTestImplementation "androidx.test:runner:version"
    androidTestImplementation "androidx.test:rules:version"

    testImplementation "org.mockito:mockito-core:version"
    testImplementation 'androidx.arch.core:core-testing:version"
    
```
