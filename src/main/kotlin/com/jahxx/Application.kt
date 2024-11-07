package com.jahxx

import com.jahxx.plugins.*
import com.jahxx.repository.ProductRepositoryImpl
import com.jahxx.repository.SaleRepositoryImpl
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val productRepository = ProductRepositoryImpl()
    val saleRepository = SaleRepositoryImpl()

    configureSerialization()
    configureDatabases()
    configureRouting(productRepository, saleRepository)
}
