package com.edumatch.models

import java.time.LocalDateTime

data class Отзыв(
    val id: String = java.util.UUID.randomUUID().toString(),
    val университетId: String,
    val оценка: Int, // от 1 до 5
    val комментарий: String,
    val плюсы: List<String>,
    val минусы: List<String>,
    val датаСоздания: LocalDateTime = LocalDateTime.now()
)

data class ОтзывЗапрос(
    val оценка: Int,
    val комментарий: String,
    val плюсы: List<String> = listOf(),
    val минусы: List<String> = listOf()
)