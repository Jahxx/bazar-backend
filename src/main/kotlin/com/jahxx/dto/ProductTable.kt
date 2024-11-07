package com.jahxx.dto

import com.jahxx.model.Product
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object ProductTable : IntIdTable("product") {
    val title = varchar("title", 255)
    val description = text("description")
    val price = decimal("price", 10, 2)
    val discountPercentage = decimal("discountPercentage", 5, 2)
    val rating = decimal("rating", 3, 2)
    val stock = integer("stock")
    val brand = varchar("brand", 255)
    val category = varchar("category", 255)
    val thumbnail = varchar("thumbnail", 255)
    val images = text("images").default("[]")
}

class ProductDao(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<ProductDao>(ProductTable)

    var title by ProductTable.title
    var description by ProductTable.description
    var price by ProductTable.price
    var discountPercentage by ProductTable.discountPercentage
    var rating by ProductTable.rating
    var stock by ProductTable.stock
    var brand by ProductTable.brand
    var category by ProductTable.category
    var thumbnail by ProductTable.thumbnail
    var images by ProductTable.images
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: ProductDao) = Product(
    dao.id.value,
    dao.title,
    dao.description,
    dao.price.toDouble(),
    dao.discountPercentage.toDouble(),
    dao.rating.toDouble(),
    dao.stock,
    dao.brand,
    dao.category,
    dao.thumbnail,
    dao.images
)