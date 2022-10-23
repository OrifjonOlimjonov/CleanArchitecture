package uz.mobiler.m1lesson84.domain.register

import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterRequest
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.register.models.RegisterData

interface RegisterRepository {

    suspend fun register(request: RegisterRequest):
            Flow<BaseResult<RegisterData, WrappedResponse<RegisterResponse>>>
}