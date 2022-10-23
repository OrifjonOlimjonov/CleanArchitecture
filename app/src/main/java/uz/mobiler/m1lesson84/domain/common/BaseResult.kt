package uz.mobiler.m1lesson84.domain.common

sealed class BaseResult<out T : Any, out U : Any> {
    data class Success<T : Any>(val data: T) : BaseResult<T, Nothing>()
    data class Error<U : Any>(val rawResponse: U) : BaseResult<Nothing, U>()
}
