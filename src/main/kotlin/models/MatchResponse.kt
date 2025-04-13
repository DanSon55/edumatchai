package com.edumatch.models

data class University(
    val id: String = java.util.UUID.randomUUID().toString(),
    val название: String,
    val страна: String,
    val рейтинг: Int,
    val стоимостьОбучения: Int,
    val шансПоступления: Double,
    val шансСтипендии: Double,
    val визоваяПолитика: String,
    val ссылкаНаПоступление: String,
    val специализации: List<String> = listOf(),
    val языкОбучения: String = "Английский"
)

data class MatchResponse(
    val университеты: List<University>,
    val всегоНайдено: Int,
    val сообщение: String = "Найдены подходящие университеты"
)