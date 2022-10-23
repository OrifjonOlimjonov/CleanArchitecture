package uz.mobiler.m1lesson84.domain.login

import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.login.remote.models.LoginRequest
import uz.mobiler.m1lesson84.data.login.remote.models.LoginResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.login.models.LoginData

interface LoginRepository {

    suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginData, WrappedResponse<LoginResponse>>>
}