package com.edumatch.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.http.*
import com.edumatch.services.RecommendationService
import com.edumatch.models.StatusResponse
import com.edumatch.util.Messages
import com.edumatch.models.MatchRequest

fun Route.recommendationRoutes() {
    val recommendationService = RecommendationService()

    route("/api/recommendations") {
        post("/подбор") {
            try {
                val запрос = call.receive<MatchRequest>()
                val рекомендации = recommendationService.getRecommendations(запрос)
                
                call.respond(StatusResponse(
                    статус = "успех",
                    сообщение = Messages.Успех.ПОИСК_ЗАВЕРШЕН,
                    данные = рекомендации
                ))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    StatusResponse(
                        статус = "ошибка",
                        сообщение = Messages.Ошибки.ОШИБКА_СЕРВЕРА,
                        ошибки = listOf(e.message ?: Messages.Ошибки.НЕВЕРНЫЙ_ЗАПРОС)
                    )
                )
            }
        }

        get("/популярные") {
            try {
                val популярные = recommendationService.getPopularUniversities()
                call.respond(StatusResponse(
                    статус = "успех",
                    сообщение = "Получен список популярных университетов",
                    данные = популярные
                ))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    StatusResponse(
                        статус = "ошибка",
                        сообщение = Messages.Ошибки.ОШИБКА_СЕРВЕРА
                    )
                )
            }
        }
    }
}