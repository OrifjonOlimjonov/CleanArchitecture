package uz.mobiler.m1lesson84.data.profile.remote.models


import com.google.gson.annotations.SerializedName

data class ProfileUserResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)