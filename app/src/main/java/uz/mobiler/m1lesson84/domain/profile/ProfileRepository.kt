package uz.mobiler.m1lesson84.domain.profile

import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData

interface ProfileRepository {

    suspend fun getProfile():
            Flow<BaseResult<ProductUserData, WrappedResponse<ProfileUserResponse>>>

}