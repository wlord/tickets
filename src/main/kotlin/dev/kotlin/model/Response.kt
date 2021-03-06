package dev.kotlin.model

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonArray
import dev.kotlin.util.TicketsDeserializer

class Response(map: Map<String, Any?>) {
    val value: String by map
    val error: Boolean by map
    val data: String by map
    val captcha: Boolean by map

    override fun equals(other: Any?): Boolean {
        if (other !is Response) {
            return false
        }
        return value == other.value && error == other.error && data == other.data && captcha == other.captcha
    }

    override fun hashCode(): Int {
        return value.hashCode() + error.hashCode() + data.hashCode() + captcha.hashCode()
    }
}

class ResponseDeserializer : TicketsDeserializer<Response>(classFromMap = { map -> Response(map) },
        property = { name, element ->
            val field = ResponseFields.valueOf(name.toUpperCase())
            Pair(field.propertyName, field.toObject(element))
        })

enum class ResponseFields(val propertyName: String, val converter: (JsonElement) -> Any) {
    VALUE("value", { jsonElement -> if (jsonElement is JsonArray) jsonElement.toString() else jsonElement.asString }),
    ERROR("error", { jsonElement -> if (jsonElement is JsonNull) false else jsonElement.asBoolean }),
    DATA("data", { jsonElement -> if (jsonElement is JsonNull) "" else jsonElement.asString }),
    CAPTCHA("captcha", { jsonElement -> if (jsonElement is JsonNull) false else jsonElement.asBoolean });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}
