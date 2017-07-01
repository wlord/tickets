package dev.kotlin.util

import dev.kotlin.DATE_FORMAT
import dev.kotlin.model.Station
import dev.kotlin.util.PropertyKeys.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class Configuration(path: String = "/application.properties") {

    val properties: Properties by lazy(LazyThreadSafetyMode.NONE) {
        val properties = Properties()
        Configuration::class.java.getResourceAsStream(path).use {
            properties.load(it)
        }
        properties
    }

    fun baseUrl(): String = properties.getProperty(BASE_URL.key)

    fun fromStation(): Station {
        return Station(mapOf(
                "id" to if (properties.getProperty(STATION_FROM_ID.key).isNotBlank())
                    properties.getProperty(STATION_FROM_ID.key).toInt() else 0,
                "name" to if (properties.getProperty(STATION_FROM_NAME.key).isNotBlank())
                    properties.getProperty(STATION_FROM_NAME.key) else "",
                "region" to ""
        ))
    }

    fun toStation(): Station {
        return Station(mapOf(
                "id" to if (properties.getProperty(STATION_TO_ID.key).isNotBlank())
                    properties.getProperty(STATION_TO_ID.key).toInt() else 0,
                "name" to if (properties.getProperty(STATION_TO_NAME.key).isNotBlank())
                    properties.getProperty(STATION_TO_NAME.key) else "",
                "region" to ""
        ))
    }

    fun departureDate(): LocalDateTime {
        return LocalDateTime.of(
                LocalDate.parse(properties.getProperty(DEPARTURE_DATE.key), DATE_FORMAT),
                LocalTime.parse(properties.getProperty(DEPARTURE_TIME.key))
        )
    }
}

enum class PropertyKeys (val key: String) {
    BASE_URL("request.baseurl"),
    STATION_FROM_ID("request.station.from.id"),
    STATION_FROM_NAME("request.station.from.name"),
    STATION_TO_ID("request.station.to.id"),
    STATION_TO_NAME("request.station.to.name"),
    DEPARTURE_DATE("request.departure.date"),
    DEPARTURE_TIME("request.departure.time")
}