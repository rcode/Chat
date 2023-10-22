package dev.rcode.android.chat.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dev.rcode.android.chat.R

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {

    @Provides
    fun providesNaveHostFragment(activity: FragmentActivity): NavController {
        val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}