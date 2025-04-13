package com.edumatch.middleware

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.util.*
import com.edumatch.util.Logger

class PerformanceMonitor {
    private val responseTimeThreshold = 1000L // 1 second

    fun intercept(context: ApplicationCall) {
        val startTime = System.currentTimeMillis()
        
        context.response.pipeline.intercept(ApplicationSendPipeline.After) { 
            val duration = System.currentTimeMillis() - startTime
            
            Logger.info("Request to ${context.request.path()} completed in ${duration}ms")
            
            if (duration > responseTimeThreshold) {
                Logger.warn("Slow request detected: ${context.request.path()} took ${duration}ms")
            }
        }
    }
}

fun Application.configurePerformanceMonitoring() {
    val monitor = PerformanceMonitor()
    
    intercept(ApplicationCallPipeline.Monitoring) {
        monitor.intercept(call)
        proceed()
    }
}