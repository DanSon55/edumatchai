package com.edumatch.models

data class MatchRequest(
    val страна: String,
    val бюджет: Int,
    val средняяОценка: Double,
    val баллSAT: Int? = null,
    val баллIELTS: Double? = null,
    val целевойРейтинг: String = "топ-100",
    val достижения: List<String> = emptyList(),
    val доходСемьи: Int? = null,
    val специальность: String? = null,
    val языкОбучения: String = "Английский"
)