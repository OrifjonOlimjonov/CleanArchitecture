package uz.mobiler.m1lesson84.domain.register.usecase

import uz.mobiler.m1lesson84.data.register.remote.models.RegisterRequest
import uz.mobiler.m1lesson84.domain.register.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {

    suspend fun invoke(registerRequest: RegisterRequest) =
        registerRepository.register(registerRequest)


}