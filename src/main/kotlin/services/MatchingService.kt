package com.edumatch.services

import com.edumatch.models.*
import com.edumatch.data.UniversityRepository
import com.edumatch.util.Logger

class MatchingService {
    private val repository = UniversityRepository()

    fun findMatches(request: MatchRequest): MatchResponse {
        Logger.info("Поиск университетов для: ${request.специальность}")
        
        val matches = repository.getAll()
            .filter { university ->
                matchesRequirements(university, request)
            }
            .sortedBy { it.рейтинг }

        return MatchResponse(
            университеты = matches,
            всегоНайдено = matches.size
        )
    }

    private fun matchesRequirements(university: University, request: MatchRequest): Boolean {
        // Проверка бюджета
        if (university.стоимостьОбучения > request.бюджет) {
            return false
        }

        // Проверка страны
        if (request.страна.isNotEmpty() && university.страна != request.страна) {
            return false
        }

        // Проверка специальности
        if (!request.специальность.isNullOrEmpty() && 
            !university.специализации.contains(request.специальность)) {
            return false
        }

        // Проверка целевого рейтинга
        val максимальныйРейтинг = when(request.целевойРейтинг) {
            "топ-10" -> 10
            "топ-50" -> 50
            "топ-100" -> 100
            "топ-200" -> 200
            else -> Int.MAX_VALUE
        }
        
        if (university.рейтинг > максимальныйРейтинг) {
            return false
        }

        return true
    }

    fun calculateMatchScore(university: University, request: MatchRequest): Double {
        var score = 0.0
        
        // Оценка по бюджету (выше балл за меньшую стоимость)
        score += (1.0 - (university.стоимостьОбучения.toDouble() / request.бюджет)) * 30

        // Оценка по рейтингу
        score += when (university.рейтинг) {
            in 1..10 -> 50.0
            in 11..50 -> 40.0
            in 51..100 -> 30.0
            in 101..200 -> 20.0
            else -> 10.0
        }

        // Бонус за совпадение страны
        if (university.страна == request.страна) {
            score += 20.0
        }

        return score
    }
}