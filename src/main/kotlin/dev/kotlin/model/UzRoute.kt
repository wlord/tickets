package dev.kotlin.model

import java.time.LocalDateTime
import java.time.temporal.TemporalAmount

data class UzRoute(val number: String, val travelTime: TemporalAmount, val from: Pair<Station, LocalDateTime>, val to: Pair<Station, LocalDateTime>, val availableTickets: Collection<Ticket>)