package com.milad.hasin_project.data.utils

import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class SafeApiRequest {

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
