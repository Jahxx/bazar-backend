package com.jahxx.model

import kotlinx.serialization.Serializable

@Serializable
data class Sale(
    val id: Int,
    val productId: Int,
    val quantity: Int,
    val totalPrice: Double,
    val purchaseDate: String
)

@Serializable
data class SaleRequest(
    val productId: Int,
    val quantity: Int,
    val totalPrice: Double,
    val purchaseDate: String
)