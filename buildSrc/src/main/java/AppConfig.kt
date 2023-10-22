import org.gradle.api.JavaVersion

// App level config constants
object AppConfig {
    const val compileSdk = 34
    const val minSdk = 24
    const val targetSdk = 34
    const val applicationId = "dev.rcode.android.chat"
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"

    val javaJvmTarget = JavaVersion.VERSION_17.toString()
    val javaCompatibility = JavaVersion.VERSION_17
}