package com.edumatch.validation

import com.edumatch.models.MatchRequest
import com.edumatch.util.Messages

class КритерииПоиска {
    fun проверитьКритерии(запрос: MatchRequest): List<String> {
        val ошибки = mutableListOf<String>()

        if (запрос.бюджет < 0) {
            ошибки.add("Бюджет не может быть отрицательным")
        }

        if (запрос.средняяОценка < 0 || запрос.средняяОценка > 4.0) {
            ошибки.add("Средняя оценка должна быть между 0 и 4.0")
        }

        запрос.баллSAT?.let {
            if (it < 400 || it > 1600) {
                ошибки.add("Балл SAT должен быть между 400 и 1600")
            }
        }

        запрос.баллIELTS?.let {
            if (it < 0 || it > 9.0) {
                ошибки.add("Балл IELTS должен быть между 0 и 9.0")
            }
        }

        if (запрос.страна.isBlank()) {
            ошибки.add("Укажите страну обучения")
        }

        return ошибки
    }

    fun проверитьФильтры(фильтры: Map<String, Any>): List<String> {
        val ошибки = mutableListOf<String>()
        
        фильтры["рейтинг"]?.let {
            if (it !is Int || it < 1) {
                ошибки.add("Неверный формат рейтинга")
            }
        }

        фильтры["стоимостьДо"]?.let {
            if (it !is Int || it < 0) {
                ошибки.add("Неверный формат максимальной стоимости")
            }
        }

        return ошибки
    }
}