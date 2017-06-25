package dev.kotlin.model

import java.time.LocalDateTime

data class RouteRequest(val baseUrl: String, val from: Station, val to: Station, val departureDate: LocalDateTime)