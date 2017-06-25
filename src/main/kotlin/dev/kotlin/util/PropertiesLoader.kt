package dev.kotlin.util

import java.util.Properties

class PropertiesLoader(path: String = "/application.properties") {

    val properties: Properties by lazy(LazyThreadSafetyMode.NONE) {
        val properties = Properties()
        PropertiesLoader::class.java.getResourceAsStream(path).use {
            properties.load(it)
        }
        properties
    }
}