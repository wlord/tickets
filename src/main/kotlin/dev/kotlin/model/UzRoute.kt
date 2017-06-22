package dev.kotlin.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAmount
import java.util.*

data class UzRoute(
        var number: String = "",
        var travelTime: TemporalAmount = Duration.ZERO,
        var from: Pair<Station, LocalDateTime>? = null,
        var to: Pair<Station, LocalDateTime>? = null,
        var availableTickets: Map<Ticket, Number> = Collections.emptyMap()
)

class UzRouteDeserializer : JsonDeserializer<UzRoute> {

    enum class RouteFields(val propertyName: String, val converter: (JsonElement) -> Any) {
        NUM("number", { jsonElement -> jsonElement.toString() }),
        TRAVEL_TIME("travelTime", {
            jsonElement -> java.time.Duration.ofMinutes(jsonElement.toString()
                .split(":")
                .map { it.toLong() }
                .reduce { hours, minutes -> hours * 60 + minutes })
        }),
        FROM("from", { jsonElement -> Pair() }),
        TILL("to"),
        TYPES("availableTickets")

        fun toObject(jsonElement: JsonElement): Any {
            return converter.invoke(jsonElement)
        }
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): UzRoute {

        val jsonObject = json!!.asJsonObject
        val route = UzRoute()
        for ((name, element) in jsonObject.entrySet().asIterable()) {

        }
    }

}