package uz.mobiler.m1lesson84.domain.login.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.login.remote.models.LoginRequest
import uz.mobiler.m1lesson84.data.login.remote.models.LoginResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.login.LoginRepository
import uz.mobiler.m1lesson84.domain.login.models.LoginData
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend fun invoke(loginRequest: LoginRequest): Flow<BaseResult<LoginData, WrappedResponse<LoginResponse>>> {
        return loginRepository.login(loginRequest)
    }
}