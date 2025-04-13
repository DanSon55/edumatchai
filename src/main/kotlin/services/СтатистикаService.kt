package com.edumatch.services

import com.edumatch.models.*
import com.edumatch.data.UniversityRepository

class СтатистикаService {
    private val репозиторий = UniversityRepository()

    fun получитьСтатистику(): Map<String, Any> {
        val университеты = репозиторий.getAll()
        
        return mapOf(
            "общаяСтатистика" to mapOf(
                "всегоУниверситетов" to университеты.size,
                "среднийРейтинг" to университеты.map { it.рейтинг }.average(),
                "средняяСтоимость" to университеты.map { it.стоимостьОбучения }.average()
            ),
            "поСтранам" to университеты
                .groupBy { it.страна }
                .mapValues { it.value.size },
            "популярныеСпециальности" to университеты
                .flatMap { it.специализации }
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedByDescending { it.second }
                .take(5)
                .toMap()
        )
    }

    fun получитьРейтингУниверситетов(фильтры: Map<String, Any>? = null): List<University> {
        var результат = репозиторий.getAll()

        фильтры?.let { ф ->
            ф["страна"]?.let { страна ->
                результат = результат.filter { it.страна == страна }
            }
            ф["максСтоимость"]?.let { макс ->
                результат = результат.filter { it.стоимостьОбучения <= макс.toString().toInt() }
            }
        }

        return результат.sortedBy { it.рейтинг }
    }
}