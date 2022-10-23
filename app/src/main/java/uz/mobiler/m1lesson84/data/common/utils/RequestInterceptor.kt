package uz.mobiler.m1lesson84.data.common.utils

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val sharedPrefs: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPrefs.getToken()
        val request = chain.request().newBuilder().addHeader("Authorization", token).build()
        return chain.proceed(request)
    }
}