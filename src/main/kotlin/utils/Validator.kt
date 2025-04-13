package com.edumatch.utils

class ValidationException(message: String) : Exception(message)

object Validator {
    fun validateMatchingCriteria(
        budget: Int?,
        preferredCountries: List<String>?,
        academicLevel: String?
    ) {
        if (budget != null && budget < 0) {
            throw ValidationException("Budget cannot be negative")
        }
        
        if (preferredCountries != null && preferredCountries.isEmpty()) {
            throw ValidationException("Preferred countries list cannot be empty")
        }
        
        if (academicLevel != null && !listOf("bachelor", "master", "phd").contains(academicLevel.toLowerCase())) {
            throw ValidationException("Invalid academic level. Must be one of: bachelor, master, phd")
        }
    }
}