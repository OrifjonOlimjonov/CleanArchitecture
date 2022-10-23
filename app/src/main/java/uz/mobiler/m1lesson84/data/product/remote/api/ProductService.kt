package uz.mobiler.m1lesson84.data.product.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.mobiler.m1lesson84.data.common.utils.WrappedListResponse
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductRequest
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductResponse
import uz.mobiler.m1lesson84.data.product.remote.models.ProductResponse
import uz.mobiler.m1lesson84.domain.product.models.ProductData

interface ProductService {

    @GET("product")
    suspend fun getAllMyProducts(): Response<WrappedListResponse<ProductResponse>>

    @POST("product")
    suspend fun createProduct(@Body createProductRequest: CreateProductRequest):Response<WrappedResponse<CreateProductResponse>>
}