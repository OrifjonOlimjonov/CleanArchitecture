package uz.mobiler.m1lesson84.domain.product

import kotlinx.coroutines.flow.Flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedListResponse
import uz.mobiler.m1lesson84.data.product.remote.models.ProductResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.models.ProductData

interface ProductRepository {

    suspend fun getAllMyProducts(): Flow<BaseResult<List<ProductData>, WrappedListResponse<ProductResponse>>>
}