package com.jahxx.dto

import com.jahxx.model.Sale
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object SaleTable: IntIdTable("sale") {
    val productId = reference("productId", ProductTable)
    val quantity = integer("quantity")
    val totalPrice = decimal("totalPrice", 10, 2)
    val purchaseDate = datetime("purchaseDate")
}

class SaleDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SaleDao>(SaleTable)

    var productId by SaleTable.productId
    var quantity by SaleTable.quantity
    var totalPrice by SaleTable.totalPrice
    var purchaseDate by SaleTable.purchaseDate
}

fun daoToModel(dao: SaleDao) = Sale(
    dao.id.value,
    dao.productId.value,
    dao.quantity,
    dao.totalPrice.toDouble(),
    dao.purchaseDate.toString()
)