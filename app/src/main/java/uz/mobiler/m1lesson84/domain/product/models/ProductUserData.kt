package uz.mobiler.m1lesson84.domain.product.models

import java.io.Serializable

data class ProductUserData(
    val id: Int,
    val name: String,
    val email: String
): Serializable
