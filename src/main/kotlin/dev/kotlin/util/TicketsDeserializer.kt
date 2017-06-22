package dev.kotlin.util

import com.google.gson.*
import java.lang.reflect.Type

class TicketsDeserializer<T>(val classFromMap: (Map<String, Any?>) -> T,
                             val property: (String) -> Pair<String, Any?>) : JsonDeserializer<T> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): T {
        val jsonObject = json!!.asJsonObject
        return classFromMap(createMap(jsonObject))
    }

    private fun createMap(jsonObject: JsonObject): Map<String, Any?> {

        val map = HashMap<String, Any?>()

        for ((name, element) in jsonObject.entrySet().asIterable()) {
            if (element is JsonNull) {
                continue
            }
            /*val field = UzResponseDeserializer.ResponseFields.valueOf(name.capitalize())
            map.put(field.propertyName, field.toObject(element))*/

            val field = property(name)
            map.put(field.first, field.second)
        }
        return map
    }

}
