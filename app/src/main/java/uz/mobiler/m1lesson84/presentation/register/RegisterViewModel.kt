package uz.mobiler.m1lesson84.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterRequest
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.register.models.RegisterData
import uz.mobiler.m1lesson84.domain.register.usecase.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Init)
    val state get() = _state.asStateFlow()

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerUseCase.invoke(registerRequest)
                .onStart {
                    _state.value = RegisterState.IsLoading(true)
                }
                .catch {
                    _state.value = RegisterState.ShowToast(it.message.toString())
                    hideLoading()
                }.collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Error -> {
                            _state.value = RegisterState.ErrorRegister(result.rawResponse)
                        }
                        is BaseResult.Success -> {
                            _state.value = RegisterState.SuccessRegister(result.data)
                        }
                    }
                }
        }
    }

    private fun hideLoading() {
        _state.value = RegisterState.IsLoading(false)
    }
}

sealed class RegisterState {
    object Init : RegisterState()
    data class IsLoading(val isLoading: Boolean) : RegisterState()
    data class ShowToast(val message: String) : RegisterState()
    data class SuccessRegister(val registerData: RegisterData) : RegisterState()
    data class ErrorRegister(val rawResponse: WrappedResponse<RegisterResponse>) : RegisterState()
}