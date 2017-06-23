package dev.kotlin.model

import com.google.gson.JsonElement
import dev.kotlin.util.TicketsDeserializer

class Station(map: Map<String, Any?>) {
    val id: Number by map
    val name: String by map
}

enum class StationFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    ID("id", { jsonElement -> jsonElement.toString() }),
    STATION("name", { jsonElement -> jsonElement.toString() });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}

class StationDeserializer : TicketsDeserializer<Station>({ map -> Station(map) },
        { name, element ->
            val field = StationFields.valueOf(name.toUpperCase())
            Pair(field.propertyName, field.toObject(element))
        })