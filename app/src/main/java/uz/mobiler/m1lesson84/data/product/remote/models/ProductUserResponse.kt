package uz.mobiler.m1lesson84.data.product.remote.models


import com.google.gson.annotations.SerializedName

data class ProductUserResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)