package uz.mobiler.m1lesson84.data.profile.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse 
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.domain.profile.models.RequestProfile

interface ProfileService {

    @GET("user/profile")
    suspend fun getProfile():Response<WrappedResponse<ProfileUserResponse>>

    @PUT("user/profile")
    suspend fun update(@Body requestProfile: RequestProfile):Response<WrappedResponse<ProfileUserResponse>>
}