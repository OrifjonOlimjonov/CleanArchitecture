package uz.mobiler.m1lesson84.presentation.profile.get

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData
import uz.mobiler.m1lesson84.domain.profile.usecase.get.ProfileUseCase
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileProcess>(ProfileProcess.Init)
    val state get() = _state.asStateFlow()

    fun getProfile() {
        viewModelScope.launch {
            profileUseCase.invoke()
                .onStart {
                    _state.value = ProfileProcess.IsLoading(true)
                }.catch {
                    showToast(it.message.toString())
                    hideLoading()
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Error ->
                            _state.value = ProfileProcess.Error(result.rawResponse)
                        is BaseResult.Success -> _state.value = ProfileProcess.Success(result.data)
                    }
                }
        }
    }

    private fun hideLoading() {
        _state.value = ProfileProcess.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = ProfileProcess.ShowToast(message)
    }

}


sealed class ProfileProcess {
    object Init : ProfileProcess()
    data class IsLoading(val isLoading: Boolean) : ProfileProcess()
    data class ShowToast(val message: String) : ProfileProcess()
    data class Success(val profileData: ProductUserData) : ProfileProcess()
    data class Error(val rawResponse: WrappedResponse<ProfileUserResponse>) : ProfileProcess()
}