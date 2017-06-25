package dev.kotlin.model

import com.google.gson.JsonElement
import dev.kotlin.util.TicketsDeserializer
import java.time.LocalDateTime

class Endpoint(map: Map<String, Any?>) {
    val station: Station by map
    val date: LocalDateTime by map

    override fun toString(): String {
        return """Endpoint(
            station = $station,
            date = $date
            )
            """
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Endpoint) {
            return false
        }
        return station == other.station && date == other.date
    }

    override fun hashCode(): Int {
        return station.hashCode() + date.hashCode()
    }
}

enum class EndpointFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    DATE("timeInSeconds", { jsonElement -> jsonElement.asLong }),
    SRC_DATE("date", { jsonElement -> LocalDateTime.parse(jsonElement.asString.replace(" ", "T")) }),
    STATION("name", { jsonElement -> jsonElement.asString });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}

class EndpointDeserializer : TicketsDeserializer<Endpoint>(
        classFromMap = { map ->
            val mapOfParams = mapOf("station" to Station(mapOf("name" to map["name"].toString(), "id" to 0)),
                    "date" to map["date"])
            Endpoint(mapOfParams) },
        property = { name, element ->
            val field = EndpointFields.valueOf(name.toUpperCase())
            Pair(field.propertyName, field.toObject(element))
        })