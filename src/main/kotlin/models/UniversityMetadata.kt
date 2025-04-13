package com.edumatch.models

data class UniversityMetadata(
    val studentCount: Int,
    val internationalStudentPercentage: Double,
    val acceptanceRate: Double,
    val averageGPA: Double,
    val averageSAT: Int,
    val averageIELTS: Double,
    val majorStrengths: List<String>,
    val researchAreas: List<String>,
    val campusSize: String,
    val location: String,
    val climate: String,
    val housingCost: Int,
    val livingExpenses: Int,
    val scholarshipTypes: List<String>,
    val employmentRate: Double,
    val averageStartingSalary: Int,
    val photos: List<String>
)

data class UniversityWithMetadata(
    val university: University,
    val metadata: UniversityMetadata
)