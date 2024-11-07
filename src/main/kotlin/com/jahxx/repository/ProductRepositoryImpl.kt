package com.jahxx.repository

import com.jahxx.dto.ProductDao
import com.jahxx.dto.ProductTable
import com.jahxx.dto.daoToModel
import com.jahxx.dto.suspendTransaction
import com.jahxx.model.Product

class ProductRepositoryImpl : ProductRepository {
    override suspend fun allProducts(query: String?, page: Int, limit: Int): List<Product> = suspendTransaction {
        val offset = ((page - 1) * limit).toLong()

        ProductDao.find {
            if (!query.isNullOrEmpty()) {
                ProductTable.title like "%$query%"
            } else {
                ProductTable.id.isNotNull()
            }
        }
            .limit(limit, offset)
            .map(::daoToModel)
    }

    override suspend fun productById(id: Int): Product? = suspendTransaction {
        ProductDao.findById(id)?.let(::daoToModel)
    }
}