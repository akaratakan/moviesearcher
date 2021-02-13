package com.atakanakar.challenge.commons

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response.success
import kotlin.Result.Companion.success


open class BaseViewModel : ViewModel() {
    /**
     *  Hata olmasi durumunda UI'da kullanmak uzere tanimlanan SingleLiveEvent
     */
    val networkErrorDetection by lazy { SingleLiveEvent<String>() }

    /**
     *  Request oncesinde true sonrasinda false olan SingleLiveEvent
     */
    val loadingDetection by lazy { SingleLiveEvent<Boolean>() }


    fun <T> sendRequest(
        client: suspend () -> T,
        onErrorAction: ((String?, Int?) -> Unit)? = null,
        onSuccess: ((T) -> Unit),
    ) {
        makeAPIRequest(client, onSuccess, onErrorAction)
    }

    private fun <T> makeAPIRequest(
        client: suspend () -> T,
        onSuccess: ((T) -> Unit)? = null,
        onErrorAction: ((String?, Int?) -> Unit)? = null
    ) {
        viewModelScope.launch {

            loadingDetection.postValue(true)

            var result: Result<T>? = null
            try {
                result = withContext(Dispatchers.IO) { Result.runCatching { (client.invoke()) } }
                startActionByResult(result, onSuccess, onErrorAction)

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                networkErrorDetection.postValue(result?.exceptionOrNull()?.message ?: "unknown error")
            }
            loadingDetection.postValue(false)
        }
    }

    private fun <T>startActionByResult(
        result: Result<T>,
        onSuccess: ((T) -> Unit)?,
        onErrorAction: ((String?, Int?) -> Unit)?
    ) {
        if (result.exceptionOrNull() == null) {
            onResultSuccess(result.getOrNull(), onSuccess)
        } else {
            onResultFailed(result, onErrorAction)
        }
    }

    private fun onResultFailed(
        result: Result<*>?,
        onErrorAction: ((String?, Int?) -> Unit)?
    ) {
        val resultException = result?.exceptionOrNull()
        if (onErrorAction != null && resultException != null) {
                val errorMessage = resultException.message
                val httpCode = if (resultException is HttpException) {
                    resultException.code()
                } else {
                    -1
                }

            onErrorAction(errorMessage, httpCode)
        } else {
            networkErrorDetection.postValue(resultException?.localizedMessage ?: "unknown error")
        }
    }


    private fun <T> onResultSuccess(result: T?, onSuccess: ((T) -> Unit)?) {
        result?.let { objectT -> onSuccess?.let { it(objectT) } }
    }



}

