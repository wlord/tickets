package dev.kotlin.model

import com.google.gson.JsonElement
import dev.kotlin.util.TicketsDeserializer
import java.time.LocalDateTime

data class Station(var id: Number, val name: String)

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
}

enum class StationFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    DATE("timeInSeconds", { jsonElement -> jsonElement.asLong }),
    SRC_DATE("date", { jsonElement -> LocalDateTime.parse(jsonElement.asString.replace(" ", "T")) }),
    STATION("name", { jsonElement -> jsonElement.toString() });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}

class EndpointDeserializer : TicketsDeserializer<Endpoint>(
        classFromMap = { map ->
            val mapOfParams = mapOf("station" to Station(0, map["name"].toString()),
                    "date" to map["date"])
            Endpoint(mapOfParams) },
        property = { name, element ->
            val field = StationFields.valueOf(name.toUpperCase())
            Pair(field.propertyName, field.toObject(element))
        })