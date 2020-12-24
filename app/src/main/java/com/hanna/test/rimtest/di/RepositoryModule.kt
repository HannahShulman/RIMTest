package com.hanna.test.rimtest.di

import com.hanna.test.rimtest.repositories.ForecastRepository
import com.hanna.test.rimtest.repositories.impl.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun forecastRepository(repository: ForecastRepositoryImpl): ForecastRepository
}