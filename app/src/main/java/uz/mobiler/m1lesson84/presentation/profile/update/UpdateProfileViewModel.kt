package uz.mobiler.m1lesson84.presentation.profile.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData
import uz.mobiler.m1lesson84.domain.profile.models.RequestProfile
import uz.mobiler.m1lesson84.domain.profile.usecase.update.UpdateProfileUseCase
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UpdateState>(UpdateState.Init)
    val state get() = _state.asStateFlow()

    fun update(requestProfile: RequestProfile) {
        viewModelScope.launch {
            updateProfileUseCase.invoke(requestProfile)
                .onStart {
                    _state.value = UpdateState.IsLoading(true)
                }.catch {
                    _state.value = UpdateState.ShowToast(it.message.toString())
                    hideLoading()
                }.collect {result->
                    hideLoading()
                    when (result) {
                        is BaseResult.Error -> {
                            _state.value = UpdateState.Error(result.rawResponse)
                        }
                        is BaseResult.Success -> {
                            _state.value = UpdateState.Success(result.data)
                        }
                    }
                }
        }
    }

    private fun hideLoading() {
        _state.value = UpdateState.IsLoading(false)
    }

}

sealed class UpdateState {
    object Init : UpdateState()
    data class IsLoading(val isLoading: Boolean) : UpdateState()
    data class ShowToast(val message: String) : UpdateState()
    data class Success(val profileData: ProductUserData) : UpdateState()
    data class Error(val rawResponse: WrappedResponse<ProfileUserResponse>) : UpdateState()
}