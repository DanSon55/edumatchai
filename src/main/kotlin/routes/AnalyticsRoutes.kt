package com.edumatch.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.http.*
import com.edumatch.services.SearchHistoryService
import com.edumatch.util.Logger

fun Route.analyticsRoutes() {
    val historyService = SearchHistoryService()

    route("/api/analytics") {
        get("/popular-countries") {
            try {
                val popularCountries = historyService.getPopularCountries()
                Logger.info("Получена статистика по популярным странам")
                call.respond(mapOf(
                    "популярныеСтраны" to popularCountries,
                    "сообщение" to "Статистика успешно загружена"
                ))
            } catch (e: Exception) {
                Logger.error("Ошибка при получении статистики стран", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("ошибка" to "Не удалось загрузить статистику")
                )
            }
        }

        get("/statistics") {
            try {
                val stats = mapOf(
                    "среднийБалл" to historyService.getAverageResultsPerCountry(),
                    "количествоПоисков" to historyService.getRecentSearches().size,
                    "популярныеНаправления" to listOf(
                        "Информатика",
                        "Экономика",
                        "Инженерия"
                    )
                )
                call.respond(stats)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("ошибка" to "Не удалось загрузить статистику")
                )
            }
        }
    }
}