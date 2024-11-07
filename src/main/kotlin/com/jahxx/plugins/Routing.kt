package com.jahxx.plugins

import com.jahxx.model.SaleRequest
import com.jahxx.repository.ProductRepository
import com.jahxx.repository.SaleRepository
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    productRepository: ProductRepository,
    saleRepository: SaleRepository
) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        staticResources("/static", "static")

        route("/api") {
            // GET: para obtener productos con búsqueda "q"
            get("/items") {
                val query = call.request.queryParameters["q"]
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10

                val products = productRepository.allProducts(query, page, limit)
                call.respond(products)
            }

            // GET: para obtener un producto específico por ID
            get("/items/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@get
                }
                val product = productRepository.productById(id)
                if (product == null) {
                    call.respond(HttpStatusCode.NotFound, "Product not found")
                    return@get
                }
                call.respond(product)
            }

            // POST: para registrar una venta
            post("/addSale") {
                try {
                    val saleRequest = call.receive<SaleRequest>()
                    val response = saleRepository.addSale(saleRequest)

                    if (response) {
                        call.respond(HttpStatusCode.Created, "Sale registered successfully")
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Failed to register sale, possibly invalid data")
                    }
                } catch (e: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid sale data: ${e.localizedMessage}")
                } catch (e: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Error processing JSON: ${e.localizedMessage}")
                }
            }

            // GET: para obtener todas las ventas
            get("/sales") {
                val sales = saleRepository.allSales()
                call.respond(sales)
            }
        }
    }
}
