package com.edumatch

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import com.edumatch.middleware.configureErrorHandling
import com.edumatch.routes.*
import io.ktor.server.http.content.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson()
    }
}

fun Application.module() {
    configureSerialization()
    configureErrorHandling()
    
    routing {
        // API routes
        matchingRoutes()
        analyticsRoutes()
        recommendationRoutes()
        
        // Static content
        static("/") {
            resources("static")
            defaultResource("static/index.html")
        }
    }
}