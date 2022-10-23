package uz.mobiler.m1lesson84.data.product.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.mobiler.m1lesson84.data.common.utils.WrappedListResponse
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.product.remote.api.ProductService
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductRequest
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductResponse
import uz.mobiler.m1lesson84.data.product.remote.models.ProductResponse
import uz.mobiler.m1lesson84.domain.common.BaseResult
import uz.mobiler.m1lesson84.domain.product.ProductRepository
import uz.mobiler.m1lesson84.domain.product.models.ProductData
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val gson: Gson
) : ProductRepository {
    override suspend fun getAllMyProducts(): Flow<BaseResult<List<ProductData>, WrappedListResponse<ProductResponse>>> {
        return flow {
            val response = productService.getAllMyProducts()
            if (response.isSuccessful) {
                val body = response.body()
                val data = body?.data
                val products = arrayListOf<ProductData>()
                data?.forEach { product ->
                    val productUserData = ProductUserData(
                        product.user.id, product.user.name,
                        product.user.email
                    )
                    products.add(
                        ProductData(
                            product.id,
                            product.productName,
                            product.price,
                            productUserData
                        )
                    )
                }
                emit(BaseResult.Success(products))
            } else {
                val type = object : TypeToken<WrappedListResponse<ProductResponse>>() {}.type
                val error = gson.fromJson<WrappedListResponse<ProductResponse>>(
                    response.errorBody()!!.charStream(), type
                )
                emit(BaseResult.Error(error))
            }
        }
    }

    override suspend fun createProduct(createProductRequest: CreateProductRequest): Flow<BaseResult<CreateProductResponse, WrappedResponse<CreateProductResponse>>> {
        return flow {
            val response = productService.createProduct(createProductRequest)
            if (response.isSuccessful) {
                val body = response.body()
                emit(BaseResult.Success(body?.data!!))
            } else {
                val type = object : TypeToken<WrappedResponse<CreateProductResponse>>() {}.type
                val error = gson.fromJson<WrappedResponse<CreateProductResponse>>(
                    response.errorBody()!!.charStream(), type
                )
                emit(BaseResult.Error(error))
            }
        }
    }
}