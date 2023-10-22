plugins {
    id(Dependencies.Plugins.application)
    id(Dependencies.Plugins.kotlinAndroid)
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = AppConfig.javaCompatibility
        targetCompatibility = AppConfig.javaCompatibility
    }

    kotlinOptions {
        jvmTarget = AppConfig.javaJvmTarget
    }

    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = true
    }

    buildFeatures {
        compose = false
        viewBinding = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    //implementation(project(":library:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":domain"))
    implementation(project(":data:main"))
    implementation(project(":feature:registration"))
    implementation(project(":feature:chat"))
    implementation(ComposeDependencies.composeActivity)
    implementation(LifeCycleDependencies.lifeCycleRuntimeKtx)

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.material3:material3")

    // Android
    implementation(Dependencies.Android.coreKts)
    implementation(Dependencies.Android.appCompat)

    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Firebase
    //implementation("com.google.firebase:firebase-core:")

    // Hilt
    DIDependencies.apply {
        implementation(hiltAndroid)
        kapt(dagerHiltCompiler)
    }

    //Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")


    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")

    // Instrumented tests
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.2")

    // Test
    testImplementation(Dependencies.Test.junit)

    // Android Test
    androidTestImplementation(Dependencies.AndroidTest.junit)
    androidTestImplementation(Dependencies.AndroidTest.espressoCore)
}