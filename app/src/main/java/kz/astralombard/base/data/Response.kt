package kz.astralombard.base.data

sealed class Response<T> {

    data class Success<T>(
        val result: T
    ) : Response<T>()

    data class Error<T>(
        val error: Exception
    ) : Response<T>()
}