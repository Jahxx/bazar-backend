package com.jahxx.repository

import com.jahxx.model.Sale
import com.jahxx.model.SaleRequest

interface SaleRepository {
    suspend fun addSale(saleRequest: SaleRequest): Boolean
    suspend fun allSales(): List<Sale>
}