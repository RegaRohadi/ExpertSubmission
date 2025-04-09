package com.rega.core.data.source

import com.rega.core.data.repository.Results
import com.rega.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


abstract class NetworkBoundResource<ResultType : Any, RequestType> {

    private var result: Flow<Results<ResultType>> = flow {
        emit(Results.Loading)
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Results.Loading)
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Results.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Results.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Results.Error(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { Results.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Results<ResultType>> = result
}