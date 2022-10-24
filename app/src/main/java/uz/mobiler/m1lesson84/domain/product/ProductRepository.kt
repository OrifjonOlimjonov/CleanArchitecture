package uz.mobiler.m1lesson84.domain.product

import uz.mobiler.m1lesson84.data.product.remote.models.ProductUpdateRequest
import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedListResponse
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductRequest
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductResponse
import uz.mobiler.m1lesson84.data.product.remote.models.ProductResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductData

interface ProductRepository {

    suspend fun getAllMyProducts(): Flow<BaseResult<List<ProductData>, WrappedListResponse<ProductResponse>>>
    suspend fun getProductById(id: String) : Flow<BaseResult<ProductData, WrappedResponse<ProductResponse>>>
    suspend fun updateProduct(productUpdateRequest: ProductUpdateRequest, id: String) : Flow<BaseResult<ProductData, WrappedResponse<ProductResponse>>>
    suspend fun deleteProductById(id: String) : Flow<BaseResult<Unit, WrappedResponse<ProductResponse>>>
    suspend fun createProduct(createProductRequest: CreateProductRequest):Flow<BaseResult<CreateProductResponse,WrappedResponse<CreateProductResponse>>>
}