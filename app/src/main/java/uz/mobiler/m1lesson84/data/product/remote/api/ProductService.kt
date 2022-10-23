package uz.mobiler.m1lesson84.data.product.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.mobiler.m1lesson84.data.common.utils.WrappedListResponse
import uz.mobiler.m1lesson84.data.product.remote.models.ProductResponse

interface ProductService {

    @GET("product")
    suspend fun getAllMyProducts(): Response<WrappedListResponse<ProductResponse>>
}