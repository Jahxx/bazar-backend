package com.jahxx.model

import com.jahxx.dto.ProductTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.insert

@Serializable
data class Product (
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: String
)

data class ProductData(
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)

fun insertProduct(product: ProductData) {
    ProductTable.insert {
        it[title] = product.title
        it[description] = product.description
        it[price] = product.price.toBigDecimal()
        it[discountPercentage] = product.discountPercentage.toBigDecimal()
        it[rating] = product.rating.toBigDecimal()
        it[stock] = product.stock
        it[brand] = product.brand
        it[category] = product.category
        it[thumbnail] = product.thumbnail
        it[images] = product.images.joinToString(prefix = "[", postfix = "]", separator = ",")
    }
}