package uz.mobiler.m1lesson84.presentation.main.product.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.data.product.remote.models.ProductUpdateRequest
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.usecase.UpdateProductUseCase
import javax.inject.Inject

@HiltViewModel
class UpdateProductViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<UpdateProductProcess>(UpdateProductProcess.Init)
    val state: StateFlow<UpdateProductProcess> get() = _state


    private fun setLoading() {
        _state.value = UpdateProductProcess.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = UpdateProductProcess.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = UpdateProductProcess.ShowToast(message)
    }

    private fun successCreate() {
        _state.value = UpdateProductProcess.SuccessUpdate
    }

    fun update(productUpdateRequest: ProductUpdateRequest, productId: String) {
        viewModelScope.launch {
            updateProductUseCase.invoke(productUpdateRequest, productId).onStart {
                setLoading()
            }.catch { exception ->
                hideLoading()
                showToast(exception.stackTraceToString())
            }.collect { result ->
                hideLoading()
                when (result) {
                    is BaseResult.Error -> showToast(result.rawResponse.message)
                    is BaseResult.Success -> successCreate()
                }
            }
        }
    }

}


sealed class UpdateProductProcess {
    object Init : UpdateProductProcess()
    object SuccessUpdate : UpdateProductProcess()
    data class IsLoading(val isLoading: Boolean) : UpdateProductProcess()
    data class ShowToast(val message: String) : UpdateProductProcess()
}