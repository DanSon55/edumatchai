package com.edumatch.models

data class StatusResponse(
    val статус: String,
    val сообщение: String,
    val данные: Any? = null,
    val ошибки: List<String> = emptyList()
)