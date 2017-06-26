package dev.kotlin.model

import dev.kotlin.util.Configuration
import java.time.LocalDateTime

data class RouteRequest(val url: String, val from: Station, val to: Station, val departureDate: LocalDateTime) {

    constructor(config: Configuration, fromStation: Station = config.fromStation(), toStation: Station = config.toStation()) : this(
            url = config.baseUrl(),
            from = fromStation,
            to = toStation,
            departureDate = config.departureDate()
    )
}