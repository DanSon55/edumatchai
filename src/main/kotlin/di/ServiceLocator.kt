package com.edumatch.di

import com.edumatch.services.*
import com.edumatch.data.UniversityRepository

object ServiceLocator {
    private val instances = mutableMapOf<Class<*>, Any>()

    init {
        register(ConfigurationService.getInstance())
        register(UniversityRepository())
        register(MetadataService())
        register(SearchHistoryService())
        register(RecommendationService())
    }

    private fun <T : Any> register(instance: T) {
        instances[instance::class.java] = instance
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: Class<T>): T {
        return instances[clazz] as? T
            ?: throw IllegalStateException("Service ${clazz.simpleName} not found")
    }

    inline fun <reified T : Any> get(): T = get(T::class.java)
}