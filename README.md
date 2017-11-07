# JSPlaygroundAndroid
JS Playground for Android

Sometimes you just have to play with stuff to understand it... here we have JavaScript and Kotlin playing together

Here we have a simple experiment involving using JS to manage a model, so that it can be shared with iOS

There is a singleton JSManager that creates the JS context, and provides a semantic interface for managing a simplistic data model.

Data Model is exposed as Kotlin classes, so it is easy to consume in Fragments, Activities and such, however the in-memory and on-disk storage is all in Javascript.

This is just a Proof of Concept to help get a feel for whether or not it is worth the effort to build the model-layer in JS

Most of the usage is in the Unit Tests currently - will expose more in the test app soon, unless we decide to abondon this approach ;-)
