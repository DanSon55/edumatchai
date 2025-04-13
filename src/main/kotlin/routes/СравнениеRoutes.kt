package com.edumatch.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import com.edumatch.services.*
import com.edumatch.models.*
import com.edumatch.util.Messages

fun Route.сравнениеRoutes() {
    val сравнениеService = СравнениеService()
    val отзывыService = ОтзывыService()

    route("/api/сравнение") {
        post {
            try {
                val ids = call.receive<List<String>>()
                if (ids.size < 2) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        StatusResponse(
                            статус = "ошибка",
                            сообщение = "Необходимо выбрать минимум 2 университета"
                        )
                    )
                    return@post
                }

                val результат = сравнениеService.сравнитьУниверситеты(ids)
                call.respond(
                    StatusResponse(
                        статус = "успех",
                        сообщение = "Сравнение выполнено",
                        данные = результат
                    )
                )
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

        post("/{id}/отзыв") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("ID не указан")
                val запрос = call.receive<ОтзывЗапрос>()
                
                val отзыв = отзывыService.добавитьОтзыв(id, запрос)
                call.respond(
                    StatusResponse(
                        статус = "успех",
                        сообщение = "Отзыв добавлен",
                        данные = отзыв
                    )
                )
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    StatusResponse(
                        статус = "ошибка",
                        сообщение = e.message ?: Messages.Ошибки.НЕВЕРНЫЙ_ЗАПРОС
                    )
                )
            }
        }

        get("/{id}/отзывы") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("ID не указан")
                val отзывы = отзывыService.получитьОтзывы(id)
                call.respond(
                    StatusResponse(
                        статус = "успех",
                        сообщение = "Отзывы получены",
                        данные = отзывы
                    )
                )
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

        get("/{id}/статистика") {
            try {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("ID не указан")
                val статистика = отзывыService.получитьСтатистику(id)
                call.respond(
                    StatusResponse(
                        статус = "успех",
                        сообщение = "Статистика получена",
                        данные = статистика
                    )
                )
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