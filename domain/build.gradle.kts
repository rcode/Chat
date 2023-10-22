plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.rcode.android.domain"
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
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation("junit:junit:4.12")
    DIDependencies.apply {
        implementation(javaxInject)
    }

    TestDependencies.apply {
        testImplementation(TestDependencies.junit)
        testImplementation(TestDependencies.junitExt)
        testImplementation(TestDependencies.coroutinesTest)
        testImplementation(TestDependencies.mokitoKotlin)
        testImplementation(Dependencies.Test.mockk)
        testImplementation(Dependencies.Test.roboeletric)
    }
}