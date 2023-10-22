object DIDependencies {
    object Version {
        const val hilt = "2.48.1"
        const val androidHiltCompiler = "1.0.0"
        const val hiltNavigationCompose = "1.0.0"
        const val hiltWork = "1.0.0"
        const val javaxInject = "1"
    }

    const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hilt}"
    const val dagerHiltCompiler = "com.google.dagger:hilt-compiler:${Version.hilt}"
    const val androidHiltCompiler = "androidx.hilt:hilt-compiler:${Version.androidHiltCompiler}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Version.hiltNavigationCompose}"
    const val hiltWork = "androidx.hilt:hilt-work:${Version.hiltWork}"
    const val javaxInject = "javax.inject:javax.inject:${Version.javaxInject}"

}