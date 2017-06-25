package dev.kotlin.model

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import dev.kotlin.util.TicketsDeserializer

class Station(map: Map<String, Any?>) {
    val id: Number by map
    val name: String by map
    val region: String by map

    override fun equals(other: Any?): Boolean {
        if (other !is Station) {
            return false
        }
        return id == other.id && name == other.name && region == other.region
    }

    override fun hashCode(): Int {
        return id.hashCode() + name.hashCode() + region.hashCode()
    }

    override fun toString(): String {
        return """Station(
            id = $id,
            name = $name
            )"""
    }
}

enum class StationFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    TITLE("name", { jsonElement -> jsonElement.asString }),
    REGION("region", { jsonElement -> if (jsonElement is JsonNull) "" else jsonElement.asString }),
    VALUE("id", { jsonElement -> jsonElement.asInt });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}

class StationDeserializer : TicketsDeserializer<Station>(
        classFromMap = { map -> Station(map) },
        property = {name, element ->
            val field = StationFields.valueOf(name.toUpperCase())
            Pair(field.propertyName, field.toObject(element))
        }
)