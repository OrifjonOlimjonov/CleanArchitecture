package uz.mobiler.m1lesson84.data.register.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterRequest
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterResponse

interface RegisterService {

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<WrappedResponse<RegisterResponse>>
}