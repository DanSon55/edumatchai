package com.edumatch.services

import com.edumatch.models.MatchRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RecommendationServiceTest {
    private lateinit var recommendationService: RecommendationService

    @BeforeEach
    fun setup() {
        recommendationService = RecommendationService()
    }

    @Test
    fun `test getRecommendations returns matches within budget`() {
        val request = MatchRequest(
            country = "USA",
            budget = 30000,
            gpa = 3.5
        )

        val recommendations = recommendationService.getRecommendations(request)
        
        assertTrue(recommendations.isNotEmpty())
        assertTrue(recommendations.all { it.annualCost <= request.budget })
    }

    @Test
    fun `test getRecommendations sorts by ranking for top universities`() {
        val request = MatchRequest(
            country = "UK",
            budget = 50000,
            gpa = 4.0,
            targetType = "топ-10"
        )

        val recommendations = recommendationService.getRecommendations(request)
        
        assertTrue(recommendations.isNotEmpty())
        assertTrue(recommendations.all { it.ranking <= 10 })
        assertEquals(recommendations, recommendations.sortedBy { it.ranking })
    }

    @Test
    fun `test getRecommendations handles empty results`() {
        val request = MatchRequest(
            country = "Mars",
            budget = 1,
            gpa = 4.0
        )

        val recommendations = recommendationService.getRecommendations(request)
        assertTrue(recommendations.isEmpty())
    }
}