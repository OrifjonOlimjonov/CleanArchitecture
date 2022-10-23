package uz.mobiler.m1lesson84.data.product.remote.models

import com.google.gson.annotations.SerializedName

data class CreateProductRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int
)