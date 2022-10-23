package uz.mobiler.m1lesson84.data.register.remote.models


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String?
)