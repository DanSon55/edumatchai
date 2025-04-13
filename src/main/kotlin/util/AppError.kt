package com.edumatch.util

class AppError(
    val code: String,
    override val message: String,
    val status: Int = 500
) : RuntimeException(message)

object ErrorMessages {
    const val INVALID_REQUEST = "Неверные параметры запроса"
    const val UNIVERSITY_NOT_FOUND = "Университет не найден"
    const val SERVER_ERROR = "Внутренняя ошибка сервера"
    const val DATABASE_ERROR = "Ошибка базы данных"
    const val VALIDATION_ERROR = "Ошибка валидации"
    const val INVALID_CREDENTIALS = "Неверные учетные данные"
    const val ACCESS_DENIED = "Доступ запрещен"
    const val RESOURCE_NOT_FOUND = "Ресурс не найден"
}