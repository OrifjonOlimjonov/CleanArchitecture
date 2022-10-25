package uz.mobiler.m1lesson84.presentation.main.product.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductRequest
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.usecase.CreateProductUseCase
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {
    private val state = MutableStateFlow<CreateProductProcess>(CreateProductProcess.Init)
    val mState: StateFlow<CreateProductProcess> get() = state

    private fun setLoading() {
        state.value = CreateProductProcess.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = CreateProductProcess.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = CreateProductProcess.ShowToast(message)
    }

    private fun successCreate() {
        state.value = CreateProductProcess.SuccessCreate
    }

    fun createProduct(productCreateRequest: CreateProductRequest) {
        viewModelScope.launch {
            createProductUseCase.invoke(productCreateRequest)
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

sealed class CreateProductProcess {
    object Init : CreateProductProcess()
    object SuccessCreate : CreateProductProcess()
    data class IsLoading(val isLoading: Boolean) : CreateProductProcess()
    data class ShowToast(val message: String) : CreateProductProcess()
}