package dev.kotlin.model

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import dev.kotlin.util.TicketsDeserializer

class UzResponse(map: Map<String, Any?>) {
    val value: String by map
    val error: Boolean by map
    val data: String by map
    val captcha: Boolean by map
}

class UzResponseDeserializer : TicketsDeserializer<UzResponse>(classFromMap = { map -> UzResponse(map) },
        property = { name, element ->
            val field = ResponseFields.valueOf(name.toUpperCase())
            Pair(field.propertyName, field.toObject(element))
        })

enum class ResponseFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    VALUE("value", { jsonElement -> jsonElement.toString() }),
    ERROR("error", { jsonElement -> if (jsonElement is JsonNull) false else jsonElement.asBoolean }),
    DATA("data", { jsonElement -> if (jsonElement is JsonNull) "" else jsonElement.asString }),
    CAPTCHA("captcha", { jsonElement -> if (jsonElement is JsonNull) false else jsonElement.asBoolean });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}
