package uz.mobiler.m1lesson84.presentation.main.product.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.usecase.DeleteProductByIdUseCase
import uz.mobiler.m1lesson84.presentation.main.product.add.CreateProductProcess
import javax.inject.Inject

@HiltViewModel
class InfoProductViewModel @Inject constructor(
    private val deleteProductByIdUseCase: DeleteProductByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DeleteProductProcess>(DeleteProductProcess.Init)
    val state: StateFlow<DeleteProductProcess> get() = _state

    private fun setLoading() {
        _state.value = DeleteProductProcess.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = DeleteProductProcess.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = DeleteProductProcess.ShowToast(message)
    }

    private fun successCreate() {
        _state.value = DeleteProductProcess.SuccessDelete
    }

    fun delete(productId: String) {
        viewModelScope.launch {
            deleteProductByIdUseCase.invoke(productId)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.stackTraceToString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> successCreate()
                        is BaseResult.Error -> showToast(result.rawResponse.message)
                    }
                }

        }
    }

}

sealed class DeleteProductProcess {
    object Init : DeleteProductProcess()
    object SuccessDelete : DeleteProductProcess()
    data class IsLoading(val isLoading: Boolean) : DeleteProductProcess()
    data class ShowToast(val message: String) : DeleteProductProcess()
}