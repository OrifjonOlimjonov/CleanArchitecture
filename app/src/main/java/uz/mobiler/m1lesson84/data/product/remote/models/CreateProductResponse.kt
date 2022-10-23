package uz.mobiler.m1lesson84.data.product.remote.models

data class CreateProductResponse(
    val `data`: Data,
    val errors: Any,
    val message: String,
    val status: Boolean
)