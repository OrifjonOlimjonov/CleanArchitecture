package uz.mobiler.m1lesson84.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductData
import uz.mobiler.m1lesson84.domain.product.usecase.GetAllMyProductUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMyProductUseCase: GetAllMyProductUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductData>>(arrayListOf())
    val products get() = _products.asStateFlow()

    init {
        getAllMyProducts()
    }

        fun getAllMyProducts() {
        viewModelScope.launch {
            getAllMyProductUseCase.invoke()
                .onStart {

                }.catch {

                }.collect { result ->
                    when (result) {
                        is BaseResult.Error -> {

                        }
                        is BaseResult.Success -> {
                            _products.value = result.data
                            Log.d("Products: ", "${result.data}")
                        }
                    }
                }
        }
    }
}