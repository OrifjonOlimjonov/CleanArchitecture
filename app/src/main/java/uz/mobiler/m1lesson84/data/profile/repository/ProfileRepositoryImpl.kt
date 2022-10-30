package uz.mobiler.m1lesson84.data.profile.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.profile.remote.api.ProfileService
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData
import uz.mobiler.m1lesson84.domain.profile.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService,
    private val gson: Gson
) : ProfileRepository {
    override suspend fun getProfile(): Flow<BaseResult<ProductUserData, WrappedResponse<ProfileUserResponse>>> {
        return flow {
            val response = profileService.getProfile()
            if (response.isSuccessful) {
                val body = response.body()
                val data = body?.data
                val profileUser = ProductUserData(data!!.id, data.name, data.email)
                emit(BaseResult.Success(profileUser))
            } else {
                val type = object : TypeToken<WrappedResponse<ProfileUserResponse>>() {}.type
                val error: WrappedResponse<ProfileUserResponse> = gson.fromJson(
                    response.errorBody()?.charStream(),
                    type
                )
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }

}