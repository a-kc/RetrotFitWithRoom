package com.example.retrofitwithroom.retorifit


import com.example.retrofitwithroom.Constants
import com.example.retrofitwithroom.model.ResponseData
import com.google.gson.Gson
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ResponseManager {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<ResponseData<T>>): ResponseData<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                if (response.body()?.status == Constants.SUCCESS) {
                    return ResponseData.success(response.body())
                } else if (response.code() == 201) {
                    return ResponseData.success(response.body())
                } else if (response.code() == 200) {
                    return ResponseData.success(response.body())
                } else {
                    return ResponseData.error(
                        response.body()?.message.toString(),
                        response.body()?.data!!
                    )
                }
            }  else {
                try {
                    val errorData = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        ResponseData::class.java
                    )
                    when {
                        response.code() == 401 -> {
                            return if(errorData.status == "4"){
                                ResponseData.error(
                                    errorData.message.toString(),
                                    emptyList()
                                )
                                //ResponseData.success(response.body())
                            } else {
                                ResponseData.error(
                                    Constants.SESSION_EXPIRE_MSG,
                                    emptyList()
                                )
                            }

                        }

                        response.code() == 406 -> {
                            return ResponseData.error(Constants.UPDATE_MEG, emptyList())
                        }

                        else -> {
                            return ResponseData.error(
                                errorData.message.toString(),
                                emptyList()                            )
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    return ResponseData.error(Constants.SERVER_ERROR, emptyList())
                }
            }

        } catch (t: Throwable) {
            t.printStackTrace()
            val message: String = when (t) {
                is ConnectException -> Constants.NETWORK_ERROR
                is UnknownHostException -> Constants.NETWORK_ERROR
                is SocketTimeoutException -> Constants.PLEASE_TRY_AGAIN
                else -> Constants.SERVER_ERROR
            }
            return ResponseData.error(message, emptyList())
        }


    }
}