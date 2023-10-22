package dev.rcode.android.chat

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        var showSplashScreen = true

        setContentView(R.layout.activity_main)




        /*setContent {
            ChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }*/

        /*splashScreen.setKeepOnScreenCondition {
            val timer = object: CountDownTimer(3000, 3000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    showSplashScreen = false
                }
            }
            timer.start()
            showSplashScreen
        }*/
    }

    override fun onStart() {
        super.onStart()
    }

    fun render() {
        // Check if user is authenticated
        // If not the take him to the registration fragment

        // Else bring user to the
    }
}


/*

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatTheme {
        Greeting("Android")
    }
}*/
