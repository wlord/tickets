package dev.kotlin.model

import com.google.gson.JsonElement
import dev.kotlin.util.GsonHelper
import dev.kotlin.util.TicketsDeserializer
import java.time.LocalDateTime
import java.time.temporal.TemporalAmount
import com.google.gson.reflect.TypeToken

class UzRoute(map: Map<String, Any?>) {
    val number: String by map
    val travelTime: TemporalAmount by map
    val from: Pair<Station, LocalDateTime> by map
    val to: Pair<Station, LocalDateTime> by map
    val availableTickets: Collection<AmountOfTickets> by map
}

class UzRouteDeserializer : TicketsDeserializer<UzRoute>({ map -> UzRoute(map) },
        { name, element ->
            val fieldsValue = name.toUpperCase()
            val field = RouteFields.valueOf(fieldsValue)
            Pair(field.propertyName, field.toObject(element))
        })


enum class RouteFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    NUM("number", { jsonElement -> jsonElement.toString() }),
    TRAVEL_TIME("travelTime", {
        jsonElement ->
        java.time.Duration.ofMinutes(jsonElement.toString()
                .split(":")
                .map { it.toLong() }
                .reduce { hours, minutes -> hours * 60 + minutes })
    }),
    FROM("from", { jsonElement -> Pair(
            GsonHelper.fromJson(jsonElement.toString(), Station::class.java),
            LocalDateTime.parse(jsonElement.asJsonObject.get("src_date").toString().replace(" ", "T"))) }),
    TILL("to", { jsonElement -> Pair(
            GsonHelper.fromJson(jsonElement.toString(), Station::class.java),
            LocalDateTime.parse(jsonElement.asJsonObject.get("src_date").toString().replace(" ", "T"))) }),
    TYPES("availableTickets", { jsonElement ->
        val type = object : TypeToken<kotlin.collections.Collection<dev.kotlin.model.AmountOfTickets>>(){}.type
        GsonHelper.fromJson(jsonElement.toString(), type) });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}