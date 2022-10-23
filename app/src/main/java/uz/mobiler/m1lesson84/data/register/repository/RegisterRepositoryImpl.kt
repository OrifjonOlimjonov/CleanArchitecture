package uz.mobiler.m1lesson84.data.register.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.register.remote.api.RegisterService
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterRequest
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.register.RegisterRepository
import uz.mobiler.m1lesson84.domain.register.models.RegisterData
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerService: RegisterService,
    private val gson: Gson
) : RegisterRepository {

    override suspend fun register(request: RegisterRequest): Flow<BaseResult<RegisterData, WrappedResponse<RegisterResponse>>> {
        return flow {
            val response = registerService.register(request)
            if (response.isSuccessful) {
                val body = response.body()
                val data = body?.data
                val registerData =
                    RegisterData(data?.email, data?.id, data?.name, data?.token, request.password)
                emit(BaseResult.Success(registerData))
            } else {
                val type = object : TypeToken<WrappedResponse<RegisterResponse>>() {}.type
                val error: WrappedResponse<RegisterResponse> = gson.fromJson(
                    response.errorBody()?.charStream(),
                    type
                )
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }
}