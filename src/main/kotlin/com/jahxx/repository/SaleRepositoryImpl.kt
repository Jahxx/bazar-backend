package com.jahxx.repository

import com.jahxx.dto.ProductTable
import com.jahxx.dto.SaleDao
import com.jahxx.dto.daoToModel
import com.jahxx.dto.suspendTransaction
import com.jahxx.model.Sale
import com.jahxx.model.SaleRequest
import org.jetbrains.exposed.dao.id.EntityID
import org.joda.time.DateTime

class SaleRepositoryImpl : SaleRepository {
    override suspend fun addSale(saleRequest: SaleRequest): Boolean = suspendTransaction {
        val sale = SaleDao.new {
            this.productId = EntityID(saleRequest.productId, ProductTable)
            this.quantity = saleRequest.quantity
            this.totalPrice = saleRequest.totalPrice.toBigDecimal()
            this.purchaseDate = DateTime.now()
        }

        return@suspendTransaction sale.id.value > 0
    }

    override suspend fun allSales(): List<Sale> = suspendTransaction {
        SaleDao.all().map(::daoToModel)
    }
}