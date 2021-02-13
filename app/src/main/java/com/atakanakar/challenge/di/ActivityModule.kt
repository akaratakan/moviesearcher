package com.atakanakar.challenge.di

import android.app.Activity
import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.atakanakar.challenge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideNavController(activity: Activity): NavController {
        return activity.findNavController(R.id.nav_host_fragment)
    }

}