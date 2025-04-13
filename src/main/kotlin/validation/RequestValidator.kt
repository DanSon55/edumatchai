package com.edumatch.validation

import com.edumatch.models.MatchRequest

class RequestValidator {
    fun validate(request: MatchRequest): List<String> {
        val errors = mutableListOf<String>()
        
        if (request.budget <= 0) {
            errors.add("Бюджет должен быть положительным числом")
        }
        
        if (request.gpa < 0 || request.gpa > 4.0) {
            errors.add("GPA должен быть от 0 до 4.0")
        }
        
        if (request.country.isBlank()) {
            errors.add("Необходимо указать страну")
        }
        
        request.sat?.let {
            if (it < 400 || it > 1600) {
                errors.add("Балл SAT должен быть от 400 до 1600")
            }
        }
        
        request.ielts?.let {
            if (it < 0 || it > 9.0) {
                errors.add("Балл IELTS должен быть от 0 до 9.0")
            }
        }

        if (!listOf("топ-10", "топ-50", "топ-100", "топ-200").contains(request.targetType)) {
            errors.add("Неверный тип целевого рейтинга. Допустимые значения: топ-10, топ-50, топ-100, топ-200")
        }
        
        return errors
    }
}