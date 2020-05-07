package com.example.catapp.data.errorModel

import com.example.catapp.R
import com.example.catapp.utils.ResourcesProvider
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ErrorModelImpl @Inject constructor(private val resourcesProvider: ResourcesProvider) :
    ErrorModel {
    override fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is HttpException -> getCodeMessage(exception.code())
            is SocketTimeoutException -> resourcesProvider.getString(R.string.timeout_error)
            else -> exception.message ?: ""
        }
    }

    private fun getCodeMessage(code: Int): String {
        return resourcesProvider.run {
            when (code) {
                404 -> getString(R.string.not_found_error)
                400 -> getString(R.string.bad_request_error)
                403 -> getString(R.string.forbidden_error)
                500 -> getString(R.string.server_error)
                else -> ""
            }
        }
    }

}