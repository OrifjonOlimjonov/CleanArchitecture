package uz.mobiler.m1lesson84.data.login.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.login.remote.api.LoginService
import uz.mobiler.m1lesson84.data.login.remote.models.LoginRequest
import uz.mobiler.m1lesson84.data.login.remote.models.LoginResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.login.LoginRepository
import uz.mobiler.m1lesson84.domain.login.models.LoginData
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    private val gson: Gson
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginData, WrappedResponse<LoginResponse>>> {
        return flow {
            val response = loginService.login(loginRequest)
            if (response.isSuccessful) {
                val body = response.body()
                val data = body?.data
                val loginData = LoginData(data?.email, data?.id, data?.name, data?.token)
                emit(BaseResult.Success(loginData))
            } else {
                val type = object : TypeToken<WrappedResponse<LoginResponse>>() {}.type
                val error: WrappedResponse<LoginResponse> =
                    gson.fromJson(response.errorBody()!!.charStream(), type)
                emit(BaseResult.Error(error))
            }
        }
    }

}