package com.edumatch.models

data class MatchRecord(
    val request: MatchRequest,
    val matches: List<University>,
    val timestamp: Long = System.currentTimeMillis()
)

data class MatchRequest(
    val country: String,
    val specialty: String,
    val gpa: Double,
    val ielts: Double,
    val sat: Int,
    val admissionYear: Int,
    val targetType: String,
    val budget: Int,
    val familyIncome: Int,
    val activities: String,
    val campusLife: Boolean = false,
    val wantWorkAfter: Boolean = false
)

data class University(
    val id: String,
    val name: String,
    val country: String,
    val ranking: Int,
    val annualCost: Int,
    val specialty: String,
    val admissionChance: Double = 0.0,
    val scholarshipChance: Double = 0.0,
    val workVisaPolicy: String,
    val applicationUrl: String
)

data class MatchResponse(
    val universities: List<University>,
    val totalMatches: Int
)

data class UniversityMetadata(
    val averageGPA: Double,
    val averageSAT: Int,
    val averageIELTS: Double,
    val acceptanceRate: Double,
    val internationalStudentPercentage: Double,
    val campusSize: Int,
    val studentToFacultyRatio: Double,
    val researchOutput: Int,
    val employmentRate: Double,
    val scholarshipTypes: List<String>
)

data class UniversityWithMetadata(
    val university: University,
    val metadata: UniversityMetadata
)

data class AnalyticsResponse(
    val trends: List<AdmissionTrend>,
    val stats: UniversityStats,
    val recommendations: Map<University, ProfileMatch>
)