package com.edumatch.util

sealed class AppError(
    val message: String,
    val code: String,
    val httpStatus: Int = 400
) : RuntimeException(message) {
    class ValidationError(message: String) : 
        AppError(message, "VALIDATION_ERROR", 400)
    
    class NotFoundError(message: String) : 
        AppError(message, "NOT_FOUND", 404)
    
    class UnauthorizedError(message: String) : 
        AppError(message, "UNAUTHORIZED", 401)
    
    class ServerError(message: String) : 
        AppError(message, "SERVER_ERROR", 500)
}

object ErrorMessages {
    val messages = mapOf(
        "gpa.invalid" to "GPA должен быть от 0 до 4.0",
        "ielts.invalid" to "Балл IELTS должен быть от 1 до 9",
        "sat.invalid" to "Балл SAT должен быть от 400 до 1600",
        "budget.invalid" to "Бюджет должен быть положительным числом",
        "search.not_found" to "Поиск не найден",
        "server.error" to "Произошла внутренняя ошибка сервера"
    )

    fun get(key: String, vararg args: Any): String {
        return messages[key]?.format(*args) 
            ?: "Неизвестная ошибка: $key"
    }
}