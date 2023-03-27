package com.webronin_26.online_mart_retailer.data.remote

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class ConnectException(val exception: Exception) : Result<Nothing>()
    data class Error(val exception: Exception) : Result<Nothing>()

    data class CreateRecordNameError (val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is ConnectException -> "ConnectException[exception=$exception]"
            is Error -> "Error[exception=$exception]"

            is CreateRecordNameError -> "CreateRecordNameError=$exception]"
        }
    }
}