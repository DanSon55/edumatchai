package com.edumatch.util

object Constants {
    val SPECIALTIES = listOf(
        "Информатика",
        "Экономика",
        "Инженерия",
        "Медицина",
        "Юриспруденция",
        "Психология",
        "Лингвистика",
        "Архитектура",
        "Менеджмент",
        "Биотехнологии"
    )

    val COUNTRIES = mapOf(
        "США" to "USA",
        "Великобритания" to "UK",
        "Германия" to "Germany",
        "Франция" to "France",
        "Канада" to "Canada",
        "Австралия" to "Australia",
        "Китай" to "China",
        "Япония" to "Japan"
    )

    val DEGREES = mapOf(
        "бакалавриат" to "bachelor",
        "магистратура" to "master",
        "аспирантура" to "phd"
    )

    val VISA_POLICIES = mapOf(
        "Доступна рабочая виза" to "Work visa available",
        "OPT доступен" to "OPT available",
        "Нет рабочей визы" to "No work visa"
    )
}