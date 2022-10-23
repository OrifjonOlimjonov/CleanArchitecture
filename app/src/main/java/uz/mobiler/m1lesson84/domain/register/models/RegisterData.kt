package uz.mobiler.m1lesson84.domain.register.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class RegisterData(
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("password")
    val password: String?
) : Parcelable