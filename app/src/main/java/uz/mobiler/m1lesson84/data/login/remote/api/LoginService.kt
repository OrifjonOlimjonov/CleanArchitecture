package uz.mobiler.m1lesson84.data.login.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.login.remote.models.LoginRequest
import uz.mobiler.m1lesson84.data.login.remote.models.LoginResponse

interface LoginService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<WrappedResponse<LoginResponse>>
}