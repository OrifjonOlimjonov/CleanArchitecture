package uz.mobiler.m1lesson84.data.login.remote.models


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)