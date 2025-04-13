package com.edumatch.services

import com.edumatch.models.*
import java.time.LocalDateTime
import java.util.UUID

data class SearchRecord(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val request: MatchRequest,
    val results: List<University>,
    val resultCount: Int = results.size
)

class SearchHistoryService {
    private val searchHistory = mutableListOf<SearchRecord>()

    fun saveSearch(request: MatchRequest, results: List<University>): SearchRecord {
        val record = SearchRecord(request = request, results = results)
        searchHistory.add(record)
        return record
    }

    fun getRecentSearches(limit: Int = 10): List<SearchRecord> {
        return searchHistory
            .sortedByDescending { it.timestamp }
            .take(limit)
    }

    fun getSearchById(id: String): SearchRecord? {
        return searchHistory.find { it.id == id }
    }

    fun getPopularCountries(): Map<String, Int> {
        return searchHistory
            .groupBy { it.request.country }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }
            .toMap()
    }

    fun getAverageResultsPerCountry(): Map<String, Double> {
        return searchHistory
            .groupBy { it.request.country }
            .mapValues { entry -> 
                entry.value.map { it.resultCount }.average()
            }
    }
}