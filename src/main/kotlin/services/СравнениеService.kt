package com.edumatch.services

import com.edumatch.models.University
import com.edumatch.data.UniversityRepository

data class РезультатСравнения(
    val университеты: List<University>,
    val сравнительныеХарактеристики: Map<String, List<Any>>,
    val рекомендации: List<String>
)

class СравнениеService {
    private val репозиторий = UniversityRepository()

    fun сравнитьУниверситеты(ids: List<String>): РезультатСравнения {
        val университеты = ids.mapNotNull { репозиторий.getById(it) }
        
        val характеристики = mapOf(
            "Стоимость обучения" to университеты.map { it.стоимостьОбучения },
            "Рейтинг" to университеты.map { it.рейтинг },
            "Шанс поступления" to университеты.map { it.шансПоступления },
            "Шанс стипендии" to университеты.map { it.шансСтипендии }
        )

        val рекомендации = generateРекомендации(университеты)

        return РезультатСравнения(
            университеты = университеты,
            сравнительныеХарактеристики = характеристики,
            рекомендации = рекомендации
        )
    }

    private fun generateРекомендации(universities: List<University>): List<String> {
        val рекомендации = mutableListOf<String>()

        // Находим самый бюджетный вариант
        val самыйБюджетный = universities.minByOrNull { it.стоимостьОбучения }
        самыйБюджетный?.let {
            рекомендации.add("${it.название} - самый экономичный вариант")
        }

        // Находим университет с самым высоким рейтингом
        val лучшийПоРейтингу = universities.minByOrNull { it.рейтинг }
        лучшийПоРейтингу?.let {
            рекомендации.add("${it.название} имеет самый высокий рейтинг")
        }

        // Находим университет с лучшими шансами поступления
        val лучшиеШансы = universities.maxByOrNull { it.шансПоступления }
        лучшиеШансы?.let {
            рекомендации.add("${it.название} предлагает наилучшие шансы поступления")
        }

        return рекомендации
    }
}