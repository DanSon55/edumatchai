package com.edumatch.models

data class AdmissionTrend(
    val year: Int,
    val acceptanceRate: Double,
    val averageCost: Int,
    val internationalEnrollment: Int,
    val scholarshipAvailability: Double
)

data class UniversityStats(
    val costRange: Pair<Int, Int>,
    val admissionRange: Pair<Double, Double>,
    val scholarshipAvailability: Double,
    val trends: List<AdmissionTrend>,
    val averageRating: Double,
    val employmentStats: EmploymentStats
)

data class EmploymentStats(
    val employmentRate: Double,
    val averageSalary: Int,
    val industryDistribution: Map<String, Double>,
    val visaSuccessRate: Double
)