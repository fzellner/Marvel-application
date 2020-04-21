package com.fzellner.data.utils

import com.fzellner.data.domain.exception.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 *
 * Function to transform safeawait into flow
 *
 */
@ExperimentalCoroutinesApi
fun <T> Deferred<Response<T>>.safeAwait(
    default: T? = null
): Flow<T> = flow {
    val response = this@safeAwait.await()

    when (response.isSuccessful) {
        true -> emit((response.body() ?: (default ?: throw NullPointerException())))
        false -> throw when(response.code()){
            409 -> MissingApiKeyException("Maybe some api key is missing. Check your api keys")
            405 -> NotAllowedException("This is not allowed")
            403 -> ForbiddenException("Forbidden")
            401 -> InvalidApiKeyException("Your api credentials is invalid. Check your credentials")
            else -> Failure.ServerFailure


        }
    }
}
