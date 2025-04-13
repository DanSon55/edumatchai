package com.edumatch.models

import java.time.LocalDateTime

data class ИсторияПоиска(
    val id: String = java.util.UUID.randomUUID().toString(),
    val времяПоиска: LocalDateTime = LocalDateTime.now(),
    val параметрыПоиска: MatchRequest,
    val результаты: List<University>,
    val количествоРезультатов: Int = результаты.size
)