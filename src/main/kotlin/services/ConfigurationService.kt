package com.edumatch.services

import com.typesafe.config.ConfigFactory
import java.io.File

class ConfigurationService {
    private val config = ConfigFactory.parseFile(File("application.conf")).resolve()

    fun getDatabaseUrl(): String = 
        config.getString("database.url")

    fun getServerPort(): Int = 
        config.getInt("server.port")

    fun getMaxSearchResults(): Int = 
        config.getInt("search.maxResults")

    fun getApiKeys(): Map<String, String> = 
        config.getObject("api.keys").unwrapped() as Map<String, String>

    companion object {
        private var instance: ConfigurationService? = null

        fun getInstance(): ConfigurationService {
            if (instance == null) {
                instance = ConfigurationService()
            }
            return instance!!
        }
    }
}