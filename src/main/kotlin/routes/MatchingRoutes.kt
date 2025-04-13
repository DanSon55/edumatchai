package com.edumatch.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import com.edumatch.models.*
import com.edumatch.services.*
import com.edumatch.validation.RequestValidator
import com.edumatch.util.Logger
import com.edumatch.utils.Validator

fun Route.matchingRoutes() {
    val matchingService = MatchingService()
    val validator = RequestValidator()
    val historyService = SearchHistoryService()

    post("/match") {
        try {
            val request = call.receive<MatchRequest>()
            Logger.info("Получен запрос на подбор университетов: страна=${request.country}, специальность=${request.specialty}")
            
            val errors = validator.validate(request)
            if (errors.isNotEmpty()) {
                Logger.error("Ошибки валидации: $errors")
                call.respond(HttpStatusCode.BadRequest, mapOf("errors" to errors))
                return@post
            }

            val matches = matchingService.findMatches(request)
            Logger.info("Найдено ${matches.totalMatches} подходящих университетов")
            
            val searchRecord = historyService.saveSearch(request, matches.universities)
            Logger.debug("Результаты поиска сохранены с ID: ${searchRecord.id}")
            
            call.respond(mapOf(
                "results" to matches,
                "searchId" to searchRecord.id
            ))
        } catch (e: Exception) {
            Logger.error("Ошибка при обработке запроса на подбор", e)
            call.respond(
                HttpStatusCode.InternalServerError, 
                mapOf("error" to "Произошла ошибка при подборе университетов")
            )
        }
    }

    get("/history") {
        try {
            val limit = call.parameters["limit"]?.toIntOrNull() ?: 10
            Logger.debug("Запрошена история поиска, лимит: $limit")
            
            val history = historyService.getRecentSearches(limit)
            call.respond(history)
        } catch (e: Exception) {
            Logger.error("Ошибка при получении истории", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Не удалось загрузить историю поиска")
            )
        }
    }

    get("/history/{id}") {
        try {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("ID не указан")
            Logger.debug("Запрошен поиск по ID: $id")
            
            val record = historyService.getSearchById(id)
            if (record != null) {
                call.respond(record)
            } else {
                Logger.error("Поиск с ID $id не найден")
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Поиск не найден"))
            }
        } catch (e: Exception) {
            Logger.error("Ошибка при получении результатов поиска", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Не удалось загрузить результаты поиска")
            )
        }
    }

    route("/api/match") {
        post {
            try {
                val params = call.receive<Map<String, Any>>()
                val matches = matchingService.findMatches(params)
                call.respond(mapOf(
                    "результаты" to matches.universities,
                    "всегоНайдено" to matches.totalMatches,
                    "сообщение" to "Найдены подходящие университеты"
                ))
            } catch (e: Exception) {
                Logger.error("Ошибка при поиске университетов", e)
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("ошибка" to "Произошла ошибка при подборе университетов")
                )
            }
        }
    }
}