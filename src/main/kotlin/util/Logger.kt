package com.edumatch.util

import org.slf4j.LoggerFactory

object Logger {
    private val logger = LoggerFactory.getLogger("EduMatch")

    fun debug(message: String) {
        logger.debug(message)
    }

    fun info(message: String) {
        logger.info(message)
    }

    fun error(message: String, e: Throwable? = null) {
        if (e != null) {
            logger.error(message, e)
        } else {
            logger.error(message)
        }
    }

    fun warn(message: String) {
        logger.warn(message)
    }
}