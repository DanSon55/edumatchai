package com.edumatch.services

object TranslationService {
    val достиженияРус = mapOf(
        "олимпиада" to "Победитель олимпиады",
        "проект" to "Исследовательский проект",
        "волонтёр" to "Волонтёрская деятельность",
        "спорт" to "Спортивные достижения",
        "языки" to "Знание языков"
    )

    val визоваяПолитикаРус = mapOf(
        "Work visa available" to "Доступна рабочая виза",
        "OPT available" to "Доступна программа OPT",
        "No work visa" to "Без рабочей визы"
    )

    val специальностиРус = mapOf(
        "Computer Science" to "Информатика",
        "Economics" to "Экономика",
        "Engineering" to "Инженерия",
        "Medicine" to "Медицина",
        "Law" to "Юриспруденция",
        "Psychology" to "Психология",
        "Linguistics" to "Лингвистика"
    )

    fun переводСтраны(страна: String): String {
        return when (страна.toLowerCase()) {
            "сша" -> "USA"
            "великобритания" -> "UK"
            "германия" -> "Germany"
            "франция" -> "France"
            "канада" -> "Canada"
            "япония" -> "Japan"
            else -> страна
        }
    }

    fun переводСпециальности(специальность: String): String {
        return специальностиРус.entries.find { it.value == специальность }?.key ?: специальность
    }
}