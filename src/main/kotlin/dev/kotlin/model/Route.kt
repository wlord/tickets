package dev.kotlin.model

import com.google.gson.JsonElement
import dev.kotlin.util.GsonHelper
import dev.kotlin.util.TicketsDeserializer
import java.time.temporal.TemporalAmount
import com.google.gson.reflect.TypeToken

class Route(map: Map<String, Any?>) {
    val number: String by map
    val model: Int by map
    val category: Int by map
    val travelTime: TemporalAmount by map
    val from: Endpoint by map
    val to: Endpoint by map
    val availableTickets: Collection<AmountOfTickets> by map
    val allowStudentDiscount: Boolean by map
    val allowTransportation: Boolean by map
    val allowBooking: Boolean by map

    override fun toString(): String {
        return """Route(
            number = $number,
            model = $model,
            category = $category,
            travelTime = $travelTime,
            from = $from,
            to = $to,
            availableTickets = $availableTickets
            )
            """
    }
}

class RouteDeserializer : TicketsDeserializer<Route>({ map -> Route(map) },
        { name, element ->
            val fieldsValue = name.toUpperCase()
            val field = RouteFields.valueOf(fieldsValue)
            Pair(field.propertyName, field.toObject(element))
        })


enum class RouteFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    NUM("number", { jsonElement -> jsonElement.toString() }),
    MODEL("model", { jsonElement -> jsonElement.asInt }),
    CATEGORY("category", { jsonElement -> jsonElement.asInt }),
    TRAVEL_TIME("travelTime", {
        jsonElement ->
        java.time.Duration.ofMinutes(jsonElement.asString
                .split(":")
                .map { it.toLong() }
                .reduce { hours, minutes -> hours * 60 + minutes })
    }),
    FROM("from", { jsonElement -> GsonHelper.fromJson(jsonElement.toString(), Endpoint::class.java) }),
    TILL("to", { jsonElement -> GsonHelper.fromJson(jsonElement.toString(), Endpoint::class.java) }),
    TYPES("availableTickets", { jsonElement ->
        val type = object : TypeToken<kotlin.collections.Collection<dev.kotlin.model.AmountOfTickets>>(){}.type
        GsonHelper.fromJson(jsonElement.toString(), type) }),
    ALLOW_STUD("allowStudentDiscount", { jsonElement -> jsonElement.asInt != 0 }),
    ALLOW_TRANSPORTATION("allowTransportation", { jsonElement -> jsonElement.asInt != 0 }),
    ALLOW_BOOKING("allowBooking", { jsonElement -> jsonElement.asInt!= 0 });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}