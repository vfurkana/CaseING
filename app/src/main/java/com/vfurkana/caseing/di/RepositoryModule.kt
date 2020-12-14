package com.vfurkana.caseing.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vfurkana.caseing.data.Repository
import com.vfurkana.caseing.data.local.SharedPreferencesHelper
import com.vfurkana.caseing.data.remote.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Singleton


@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    const val SHARED_PREF_NAME = "CASEING_SHARED_PREFERENCES"

    @Provides
    fun provideGson(): Gson = GsonBuilder().serializeNulls().create()

    @Provides
    @ActivityRetainedScoped
    fun provideSharedPreferencesHelper(sharedPreferences: SharedPreferences, gson: Gson): SharedPreferencesHelper {
        return SharedPreferencesHelper(sharedPreferences, gson)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepository(githubService: GithubService, sharedPreferencesHelper: SharedPreferencesHelper): Repository {
        return Repository(githubService, sharedPreferencesHelper)
    }
}