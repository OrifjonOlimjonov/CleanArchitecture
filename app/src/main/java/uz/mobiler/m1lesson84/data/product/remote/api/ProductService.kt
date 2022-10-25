package uz.mobiler.m1lesson84.data.product.remote.api

import retrofit2.Response
import retrofit2.http.*
import uz.mobiler.m1lesson84.data.common.utils.WrappedListResponse
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductRequest
import uz.mobiler.m1lesson84.data.product.remote.models.ProductResponse
import uz.mobiler.m1lesson84.data.product.remote.models.ProductUpdateRequest

interface ProductService {

    @GET("product")
    suspend fun getAllMyProducts(): Response<WrappedListResponse<ProductResponse>>

    @GET("product/{id}")
    suspend fun getProductById(@Path("id") id: String) : Response<WrappedResponse<ProductResponse>>

    @PUT("product/{id}")
    suspend fun updateProduct(@Body productUpdateRequest: ProductUpdateRequest, @Path("id") id: String) : Response<WrappedResponse<ProductResponse>>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") id: String) : Response<WrappedResponse<ProductResponse>>

    @POST("product")
    suspend fun createProduct(@Body createProductRequest: CreateProductRequest) : Response<WrappedResponse<ProductResponse>>

}