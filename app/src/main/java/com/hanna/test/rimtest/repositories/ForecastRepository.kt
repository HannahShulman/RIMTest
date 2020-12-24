package com.hanna.test.rimtest.repositories

import com.hanna.test.rimtest.datasource.Resource
import com.hanna.test.rimtest.entities.StopInfoResponse
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    fun fetchForecast(): Flow<Resource<StopInfoResponse>>
}