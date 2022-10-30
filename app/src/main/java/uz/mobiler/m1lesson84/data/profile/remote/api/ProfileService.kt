package uz.mobiler.m1lesson84.data.profile.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse 
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse

interface ProfileService {

    @GET("user/profile")
    suspend fun getProfile():Response<WrappedResponse<ProfileUserResponse>>
}