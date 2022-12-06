package com.khaledsh.data.api.util

import com.khaledsh.data.R
import com.khaledsh.domain.models.Failure
import com.khaledsh.domain.models.Success
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

const val TIMEOUT_ERROR_STATUS_CODE = 408

class ApiHelper @Inject constructor(private val networkInfoHelper: NetworkInfoHelper) {


    val isNetworkConnected: Boolean
        get() = networkInfoHelper.isInternetAvailable()

    inline fun <T, R> safeExecute(
        block: () -> Response<T>,
        transform: (T) -> R
    ) =
        if (isNetworkConnected) {
            try {
                block().extractResponseBody(transform)
            } catch (e: IOException) {
                Failure(
                    messageRes = com.khaledsh.data.R.string.reason_timeout,
                    code = TIMEOUT_ERROR_STATUS_CODE
                )
            }
        } else {
            Failure(
                messageRes = com.khaledsh.data.R.string.reason_network,
                code = TIMEOUT_ERROR_STATUS_CODE
            )
        }

    inline fun <T, R> Response<T>.extractResponseBody(transform: (T) -> R) =
        if (isSuccessful) {
            body()?.let {
                Success(transform(it))
            } ?: Failure(
                messageRes = com.khaledsh.data.R.string.reason_network,
                code = TIMEOUT_ERROR_STATUS_CODE
            )
        } else {
            val errorBody = errorBody()?.string() ?: ""
            val code = code()
            try {
                handleError(JSONObject(errorBody), code)
            } catch (e: Exception) {
                if (errorBody.isNotEmpty()) {
                    Failure(message = errorBody, code = code)
                } else {
                    Failure(
                        messageRes = com.khaledsh.data.R.string.reason_generic,
                        code = code
                    )
                }
            }
        }

    /**
     * Return error messages according to the error code
     */
    fun handleError(json: JSONObject, code: Int): Failure {
        return when (json.getInt("code")) {
            500 -> {
                Failure(messageRes = R.string.reason_response, code = code)
            }
            else -> {
                Failure(message = json.getString("msg"), code = code)
            }
        }
    }
}