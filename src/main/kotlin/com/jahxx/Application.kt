package com.jahxx

import com.jahxx.plugins.*
import com.jahxx.repository.ProductRepositoryImpl
import com.jahxx.repository.SaleRepositoryImpl
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        anyHost()
    }

    val productRepository = ProductRepositoryImpl()
    val saleRepository = SaleRepositoryImpl()
    configureSerialization()
    configureDatabases()
    configureRouting(productRepository, saleRepository)
}
