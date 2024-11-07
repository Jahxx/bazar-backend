package com.jahxx.repository

import com.jahxx.model.Product

interface ProductRepository {
    suspend fun allProducts(query: String?, page: Int, limit: Int): List<Product>
    suspend fun productById(id: Int): Product?
}