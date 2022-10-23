package uz.mobiler.m1lesson84.data.common.utils

data class WrappedResponse<T>(
    var code: Int,
    var message: String,
    var status: Boolean,
    var errors: List<String>? = null,
    var data: T? = null
)

data class WrappedListResponse<T>(
    var code: Int,
    var message: String,
    var status: Boolean,
    var errors: List<String>? = null,
    var data: List<T>? = null
)