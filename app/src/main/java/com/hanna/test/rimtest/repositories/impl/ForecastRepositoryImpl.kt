package com.hanna.test.rimtest.repositories.impl

import com.hanna.test.rimtest.datasource.Api
import com.hanna.test.rimtest.datasource.ApiResponse
import com.hanna.test.rimtest.datasource.ForecastRequestProvider
import com.hanna.test.rimtest.datasource.Resource
import com.hanna.test.rimtest.datasource.network.FlowNetworkOnlyResource
import com.hanna.test.rimtest.entities.StopInfo
import com.hanna.test.rimtest.entities.StopInfoResponse
import com.hanna.test.rimtest.repositories.ForecastRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(val api: Api) : ForecastRepository {

    override fun fetchForecast(): Flow<Resource<StopInfoResponse>> {
        val info = ForecastRequestProvider.requestInfo()
        return object : FlowNetworkOnlyResource<StopInfoResponse, StopInfo>() {
            override fun processResponse(response: StopInfo): StopInfoResponse {
                return StopInfoResponse().convertToResponseFromStopInfo(response, info.direction)
            }

            override suspend fun fetchFromNetwork(): Flow<ApiResponse<StopInfo>> {
                return api.getTramForecast(info.stopCode)
            }

        }.asFlow()
    }
}