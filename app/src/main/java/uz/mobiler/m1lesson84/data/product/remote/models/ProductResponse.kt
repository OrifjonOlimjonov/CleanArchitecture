package uz.mobiler.m1lesson84.data.product.remote.models


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("user")
    val user: ProductUserResponse
)