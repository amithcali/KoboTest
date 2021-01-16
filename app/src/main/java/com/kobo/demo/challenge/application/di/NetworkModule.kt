package com.kobo.demo.challenge.application.di




import com.kobo.demo.challenge.application.data.APIService
import com.kobo.demo.challenge.application.repo.UserRepository
import com.kobo.demo.challenge.application.repo.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideAPIService(): APIService {
        return APIService.create()
    }

    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl(provideAPIService())
    }
}
