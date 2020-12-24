package com.hanna.test.rimtest.datasource

import com.hanna.test.rimtest.entities.StopInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


//Prototypes - N/A
//Tests - N/A
interface Api {

    @GET("get.ashx?action=forecast&encrypt=false")
    fun getTramForecast(@Query("stop") stop: String): Flow<ApiResponse<StopInfo>>
}