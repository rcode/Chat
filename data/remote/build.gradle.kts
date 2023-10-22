import DIDependencies.dagerHiltCompiler
import DIDependencies.hiltAndroid

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "dev.rcode.android.data.remote"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(":core:base"))
    implementation(project(":domain"))
    implementation("androidx.core:core-ktx:1.9.0")
    api(Dependencies.ThirdParty.retrofit)
    api(Dependencies.ThirdParty.retrofitConverterGson)

    // Firebase Realtime Database
    // Import the BoM for the Firebase platform
    //implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    //api("com.google.firebase:firebase-database-ktx")
    api("com.google.firebase:firebase-database-ktx:20.2.2")

    // Hilt
    DIDependencies.apply {
        implementation(hiltAndroid)
        kapt(dagerHiltCompiler)
    }

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}