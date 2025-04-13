package com.edumatch.middleware

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import com.edumatch.models.StatusResponse
import com.edumatch.util.Messages

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                StatusResponse(
                    статус = "ошибка",
                    сообщение = Messages.Ошибки.ОШИБКА_СЕРВЕРА,
                    ошибки = listOf(cause.message ?: "Неизвестная ошибка")
                )
            )
        }
        
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                StatusResponse(
                    статус = "ошибка",
                    сообщение = Messages.Ошибки.НЕВЕРНЫЙ_ЗАПРОС,
                    ошибки = listOf(cause.message ?: "Неверные параметры запроса")
                )
            )
        }
        
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(
                HttpStatusCode.NotFound,
                StatusResponse(
                    статус = "ошибка",
                    сообщение = Messages.Ошибки.УНИВЕРСИТЕТ_НЕ_НАЙДЕН
                )
            )
        }
    }
}