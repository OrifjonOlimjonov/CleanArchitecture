package uz.mobiler.m1lesson84.domain.profile.models


import com.google.gson.annotations.SerializedName

data class ProductUserData(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)