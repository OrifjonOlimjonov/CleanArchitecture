package uz.mobiler.m1lesson84.domain.product.models

import java.io.Serializable

data class ProductData(
    val id: Int,
    val name: String,
    val price: Int,
    val user: ProductUserData
):Serializable