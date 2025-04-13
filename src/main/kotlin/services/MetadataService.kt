package com.edumatch.services

import com.edumatch.models.University

data class UniversityMetadata(
    val averageGPA: Double,
    val averageSAT: Int,
    val averageIELTS: Double,
    val acceptanceRate: Double,
    val scholarshipTypes: List<String>
)

data class UniversityWithMetadata(
    val university: University,
    val metadata: UniversityMetadata
)

class MetadataService {
    private val metadata = mutableMapOf<String, UniversityMetadata>()

    init {
        // Sample metadata - в реальном приложении будет загружаться из базы данных
        metadata["MIT"] = UniversityMetadata(
            averageGPA = 3.9,
            averageSAT = 1500,
            averageIELTS = 7.5,
            acceptanceRate = 7.0,
            scholarshipTypes = listOf("Merit-based", "Need-based", "Research")
        )
    }

    fun enrichUniversityWithMetadata(university: University): UniversityWithMetadata {
        val universityMetadata = metadata[university.name] ?: UniversityMetadata(
            averageGPA = 3.0,
            averageSAT = 1200,
            averageIELTS = 6.5,
            acceptanceRate = 50.0,
            scholarshipTypes = listOf("Merit-based")
        )
        
        return UniversityWithMetadata(university, universityMetadata)
    }

    fun updateMetadata(universityName: String, metadata: UniversityMetadata) {
        this.metadata[universityName] = metadata
    }
}