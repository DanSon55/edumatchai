package com.edumatch.services

import com.edumatch.models.*

data class ProfileMatch(
    val score: Double,
    val reasons: List<String>
)

class RecommendationService {
    fun getRecommendations(
        request: MatchRequest,
        universities: List<University>,
        history: List<MatchRecord>
    ): Map<University, ProfileMatch> {
        return universities.associateWith { university ->
            calculateProfileMatch(request, university, history)
        }.filter { it.value.score >= 0.6 }
         .toSortedMap(compareByDescending { calculateOverallScore(it) })
    }

    private fun calculateProfileMatch(
        request: MatchRequest,
        university: University,
        history: List<MatchRecord>
    ): ProfileMatch {
        val reasons = mutableListOf<String>()
        var score = 0.0
        var factorsCount = 0

        // Анализ академического соответствия
        val academicScore = calculateAcademicScore(request, university)
        score += academicScore
        factorsCount++
        if (academicScore > 0.7) {
            reasons.add("Ваш академический профиль хорошо соответствует требованиям")
        }

        // Анализ бюджета
        if (university.annualCost <= request.budget) {
            score += 1.0
            factorsCount++
            if (university.annualCost <= request.budget * 0.8) {
                reasons.add("Стоимость обучения укладывается в ваш бюджет с запасом")
            }
        }

        // Анализ карьерных перспектив
        if (request.wantWorkAfter && university.workVisaPolicy.contains("разрешено")) {
            score += 1.0
            factorsCount++
            reasons.add("Хорошие возможности для работы после выпуска")
        }

        // Анализ исторических данных
        val similarProfiles = history.filter { record ->
            record.request.gpa >= request.gpa - 0.3 &&
            record.request.gpa <= request.gpa + 0.3 &&
            record.request.budget >= request.budget * 0.8 &&
            record.request.budget <= request.budget * 1.2
        }

        val successfulApplications = similarProfiles.count { record ->
            record.matches.any { it.name == university.name && it.admissionChance >= 0.7 }
        }

        if (similarProfiles.isNotEmpty()) {
            val successRate = successfulApplications.toDouble() / similarProfiles.size
            score += successRate
            factorsCount++
            if (successRate >= 0.7) {
                reasons.add("Высокий процент успешных поступлений у похожих кандидатов")
            }
        }

        // Анализ специальности
        if (university.specialty == request.specialty) {
            score += 1.0
            factorsCount++
            reasons.add("Специальность полностью соответствует вашим интересам")
        }

        return ProfileMatch(
            score = score / factorsCount,
            reasons = reasons
        )
    }

    private fun calculateAcademicScore(request: MatchRequest, university: University): Double {
        var score = 0.0

        // GPA оценка
        score += when {
            request.gpa >= 3.8 -> 1.0
            request.gpa >= 3.5 -> 0.8
            request.gpa >= 3.0 -> 0.6
            else -> 0.4
        }

        // IELTS оценка
        score += when {
            request.ielts >= 7.5 -> 1.0
            request.ielts >= 7.0 -> 0.8
            request.ielts >= 6.5 -> 0.6
            else -> 0.4
        }

        // SAT оценка
        score += when {
            request.sat >= 1500 -> 1.0
            request.sat >= 1400 -> 0.8
            request.sat >= 1300 -> 0.6
            else -> 0.4
        }

        return score / 3.0
    }

    private fun calculateOverallScore(university: University): Double {
        return university.ranking.toDouble() * -0.3 + 
               university.admissionChance * 0.4 +
               university.scholarshipChance * 0.3
    }
}