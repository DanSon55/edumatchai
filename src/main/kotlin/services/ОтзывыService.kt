package com.edumatch.services

import com.edumatch.models.Отзыв
import com.edumatch.models.ОтзывЗапрос
import com.edumatch.util.Logger
import java.util.concurrent.ConcurrentHashMap

class ОтзывыService {
    private val отзывы = ConcurrentHashMap<String, MutableList<Отзыв>>()

    fun добавитьОтзыв(университетId: String, запрос: ОтзывЗапрос): Отзыв {
        if (запрос.оценка !in 1..5) {
            throw IllegalArgumentException("Оценка должна быть от 1 до 5")
        }

        val отзыв = Отзыв(
            университетId = университетId,
            оценка = запрос.оценка,
            комментарий = запрос.комментарий,
            плюсы = запрос.плюсы,
            минусы = запрос.минусы
        )

        отзывы.getOrPut(университетId) { mutableListOf() }.add(отзыв)
        Logger.info("Добавлен новый отзыв для университета: $университетId")
        
        return отзыв
    }

    fun получитьОтзывы(университетId: String): List<Отзыв> {
        return отзывы[университетId] ?: emptyList()
    }

    fun получитьСтатистику(университетId: String): Map<String, Any> {
        val универОтзывы = отзывы[университетId] ?: return emptyMap()
        
        return mapOf(
            "средняяОценка" to универОтзывы.map { it.оценка }.average(),
            "количествоОтзывов" to универОтзывы.size,
            "распределениеОценок" to универОтзывы
                .groupBy { it.оценка }
                .mapValues { it.value.size },
            "частыеПлюсы" to универОтзывы
                .flatMap { it.плюсы }
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedByDescending { it.second }
                .take(5),
            "частыеМинусы" to универОтзывы
                .flatMap { it.минусы }
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedByDescending { it.second }
                .take(5)
        )
    }
}