package uz.mobiler.m1lesson84.presentation.main.product.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductRequest
import uz.mobiler.m1lesson84.domain.product.usecase.CreateProductUseCase
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private var createProductUseCase: CreateProductUseCase,
):ViewModel() {
    private val _products = MutableStateFlow(CreateProductRequest("",-1))
    val products get() = _products.asStateFlow()

    init{
        createProduct()
    }

    private fun createProduct() {
        viewModelScope.launch {

        }
    }
}