package dev.kotlin.model

import com.google.gson.JsonElement
import dev.kotlin.util.TicketsDeserializer

class UzResponse(map: Map<String, Any?>) {
    val value: String by map
    val error: Boolean by map
    val data: String by map
    val captcha: Boolean by map
}

class UzResponseDeserializer : TicketsDeserializer<UzResponse>({ map -> UzResponse(map) },
        { name, element ->
            val field = ResponseFields.valueOf(name.capitalize())
            Pair(field.propertyName, field.toObject(element))
        })

enum class ResponseFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    VALUE("value", { jsonElement -> jsonElement.toString() }),
    ERROR("error", { jsonElement -> jsonElement.asBoolean }),
    DATA("data", { jsonElement -> jsonElement.asString }),
    CAPTCHA("captcha", { jsonElement -> jsonElement.asBoolean });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}
