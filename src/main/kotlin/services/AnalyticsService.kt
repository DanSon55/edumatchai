package com.edumatch.services

import com.edumatch.models.*

data class AdmissionTrend(
    val year: Int,
    val acceptanceRate: Double,
    val averageCost: Int,
    val internationalStudentPercentage: Double
)

data class UniversityStats(
    val costRange: Pair<Int, Int>,
    val admissionRange: Pair<Double, Double>,
    val scholarshipAvailability: Double,
    val trends: List<AdmissionTrend>
)

class AnalyticsService {
    // Временные данные для трендов - будут заменены на реальные из базы данных
    private val trends = mapOf(
        "США" to listOf(
            AdmissionTrend(2020, 15.0, 52000, 25.0),
            AdmissionTrend(2021, 14.5, 53500, 26.0),
            AdmissionTrend(2022, 13.8, 55000, 27.5),
            AdmissionTrend(2023, 13.2, 56500, 28.0)
        ),
        "Великобритания" to listOf(
            AdmissionTrend(2020, 18.0, 42000, 30.0),
            AdmissionTrend(2021, 17.5, 43000, 31.0),
            AdmissionTrend(2022, 17.0, 44000, 32.0),
            AdmissionTrend(2023, 16.5, 45000, 33.0)
        )
    )

    fun getCountryTrends(country: String): List<AdmissionTrend> {
        return trends[country] ?: emptyList()
    }

    fun analyzeUniversities(universities: List<University>): UniversityStats {
        if (universities.isEmpty()) {
            return UniversityStats(
                costRange = Pair(0, 0),
                admissionRange = Pair(0.0, 0.0),
                scholarshipAvailability = 0.0,
                trends = emptyList()
            )
        }

        val costs = universities.map { it.annualCost }
        val admissionChances = universities.map { it.admissionChance }
        val scholarshipChances = universities.map { it.scholarshipChance }

        return UniversityStats(
            costRange = Pair(costs.minOrNull()!!, costs.maxOrNull()!!),
            admissionRange = Pair(
                admissionChances.minOrNull()!!,
                admissionChances.maxOrNull()!!
            ),
            scholarshipAvailability = scholarshipChances.average(),
            trends = trends[universities.first().country] ?: emptyList()
        )
    }
}