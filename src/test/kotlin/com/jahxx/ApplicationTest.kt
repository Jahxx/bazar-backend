package com.jahxx

import com.jahxx.plugins.configureRouting
import com.jahxx.repository.ProductRepositoryImpl
import com.jahxx.repository.SaleRepositoryImpl
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            val productRepository = ProductRepositoryImpl()
            val saleRepository = SaleRepositoryImpl()

            configureRouting(productRepository, saleRepository)
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
