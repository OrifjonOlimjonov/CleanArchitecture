package uz.mobiler.m1lesson84.domain.profile.usecase.update

import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData
import uz.mobiler.m1lesson84.domain.profile.ProfileRepository
import uz.mobiler.m1lesson84.domain.profile.models.RequestProfile
import javax.inject.Inject


class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun invoke(profile: RequestProfile): Flow<BaseResult<ProductUserData, WrappedResponse<ProfileUserResponse>>>{
        return profileRepository.updateProfile(profile)
    }
}