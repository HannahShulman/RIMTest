package com.hanna.test.rimtest.datasource.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.hanna.test.rimtest.datasource.ApiErrorResponse
import com.hanna.test.rimtest.datasource.ApiResponse
import com.hanna.test.rimtest.datasource.ApiSuccessResponse
import com.hanna.test.rimtest.datasource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take


/**
 * Based on the logic implemented here: (NetworkBoundResource)
 * https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt
 */
abstract class FlowNetworkOnlyResource<ResultType, RequestType> {

    fun asFlow() = flow<Resource<ResultType>> {
        emit(Resource.loading(null))
        fetchFromNetwork().take(1).collect { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    emit(Resource.success(processResponse(apiResponse.body)))
                }
                is ApiErrorResponse -> {
                    emit(Resource.error(apiResponse.errorMessage, null))
                }
                else -> {
                }
            }
        }

    }

    protected open fun onFetchFailed() {
        // Implement in sub-classes to handle errors
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>
}
