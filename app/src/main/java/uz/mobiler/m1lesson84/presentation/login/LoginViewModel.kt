package uz.mobiler.m1lesson84.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.login.remote.models.LoginRequest
import uz.mobiler.m1lesson84.data.login.remote.models.LoginResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.login.models.LoginData
import uz.mobiler.m1lesson84.domain.login.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LoginEventState>(LoginEventState.Init)
    val state get() = _state.asStateFlow()

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.invoke(loginRequest)
                .onStart {
                    setLoading()
                }.catch {
                    hideLoading()
                    showErrorToast(it.message.toString())
                }.collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Error -> _state.value =
                            LoginEventState.ErrorLogin(result.rawResponse)
                        is BaseResult.Success -> _state.value =
                            LoginEventState.SuccessLogin(result.data)
                    }
                }
        }
    }

    private fun showErrorToast(message: String) {
        _state.value = LoginEventState.ShowToast(message)
    }

    private fun setLoading() {
        _state.value = LoginEventState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = LoginEventState.IsLoading(false)
    }
}

sealed class LoginEventState {
    object Init : LoginEventState()
    data class IsLoading(val isLoading: Boolean) : LoginEventState()
    data class ShowToast(val message: String) : LoginEventState()
    data class SuccessLogin(val loginData: LoginData) : LoginEventState()
    data class ErrorLogin(val rawResponse: WrappedResponse<LoginResponse>) : LoginEventState()
}