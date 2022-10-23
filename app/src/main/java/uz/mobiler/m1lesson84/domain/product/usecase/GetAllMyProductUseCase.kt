package uz.mobiler.m1lesson84.domain.product.usecase

import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedListResponse
import uz.mobiler.m1lesson84.data.product.remote.models.ProductResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.ProductRepository
import uz.mobiler.m1lesson84.domain.product.models.ProductData
import javax.inject.Inject

class GetAllMyProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun invoke(): Flow<BaseResult<List<ProductData>, WrappedListResponse<ProductResponse>>> {
        return productRepository.getAllMyProducts()
    }
}