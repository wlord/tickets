package dev.kotlin.model

import com.google.gson.*
import dev.kotlin.model.UzResponseDeserializer.ResponseFields.*
import java.lang.reflect.Type

class UzResponse(map: Map<String, Any?>) {
    val value: String by map
    val error: Boolean by map
    val data: String by map
    val captcha: Boolean by map
}

class UzResponseDeserializer : JsonDeserializer<UzResponse> {

    enum class ResponseFields(val propertyName: String) {
        VALUE("value") {
            override fun toObject(jsonElement: JsonElement): Any {
                return jsonElement.toString()
            }
        },
        ERROR("error") {
            override fun toObject(jsonElement: JsonElement): Any {
                return jsonElement.asBoolean
            }
        },
        DATA("data") {
            override fun toObject(jsonElement: JsonElement): Any {
                return jsonElement.asString
            }
        },
        CAPTCHA("captcha") {
            override fun toObject(jsonElement: JsonElement): Any {
                return jsonElement.asBoolean
            }
        };

        abstract fun toObject(jsonElement: JsonElement): Any
    }


    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): UzResponse {

        val jsonObject = json!!.asJsonObject
        return UzResponse(createMap(jsonObject))
    }

    fun createMap(jsonObject: JsonObject): Map<String, Any?> {

        val map = HashMap<String, Any?>()

        for ((name, element) in jsonObject.entrySet().asIterable()) {
            if (element is JsonNull) {
                continue
            }
            val field = valueOf(name.capitalize())
            map.put(field.propertyName, field.toObject(element))
        }
        return map
    }
}