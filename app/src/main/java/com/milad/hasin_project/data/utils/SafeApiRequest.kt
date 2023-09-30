package com.milad.hasin_project.data.utils

import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * The `SafeApiRequest` class is an abstract class providing a mechanism for making safe API requests
 * by handling exceptions and emitting results using Flow.
 */
abstract class SafeApiRequest {

    /**
     * Executes an API request in a safe manner and emits the result using a Flow.
     *
     * @param call The suspend function representing the API request.
     * @return A [Flow] emitting a [Result] containing the result of the API request.
     */
    suspend fun <T : Any> apiRequest(
        call: suspend () -> Response<T>
    ) =
        flow {
            emit(Result.loading(null))
            try {
                val response = call.invoke()
                if (response.isSuccessful)
                    emit(
                        Result.success(
                            data = response.body()
                        )
                    )
                else
                    emit(
                        Result.error(
                            msg = "Connection failed: ${response.errorBody()?.string()}",
                            data = null
                        )
                    )
            } catch (ex: Exception) {
                emit(
                    Result.error(
                        msg = "Connection error: ${ex.message}",
                        data = null
                    )
                )
            }
        }
}
