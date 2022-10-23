package uz.mobiler.m1lesson84.domain.login.models

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("token")
    val token: String?
)
