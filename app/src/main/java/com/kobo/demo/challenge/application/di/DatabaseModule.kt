package com.kobo.demo.challenge.application.di

import android.content.Context
import com.kobo.demo.challenge.application.home.HomeActivity
import com.kobo.demo.challenge.application.home.HomeViewModel
import com.kobo.demo.challenge.application.local.UserDao
import com.kobo.demo.challenge.application.local.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): UserDatabase {
        return UserDatabase.getInstance(context)
    }

    @Provides
    fun provideUserDao(appDatabase: UserDatabase): UserDao {
        return appDatabase.userDao()
    }

}
