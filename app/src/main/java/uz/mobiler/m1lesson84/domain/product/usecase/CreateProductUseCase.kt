package uz.mobiler.m1lesson84.domain.product.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductRequest
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.ProductRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun create(createProductRequest: CreateProductRequest): Flow<BaseResult<CreateProductResponse, WrappedResponse<CreateProductResponse>>> {
        return productRepository.createProduct(createProductRequest)
    }
}