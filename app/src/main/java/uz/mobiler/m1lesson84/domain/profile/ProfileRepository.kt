package uz.mobiler.m1lesson84.domain.profile

import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData
import uz.mobiler.m1lesson84.domain.profile.models.RequestProfile

interface ProfileRepository {

    suspend fun getProfile(): Flow<BaseResult<ProductUserData, WrappedResponse<ProfileUserResponse>>>

    suspend fun updateProfile(update: RequestProfile):Flow<BaseResult<ProductUserData, WrappedResponse<ProfileUserResponse>>>
}