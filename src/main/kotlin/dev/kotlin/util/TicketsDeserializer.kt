package dev.kotlin.util

import com.google.gson.*
import dev.kotlin.model.*
import java.lang.reflect.Type

open class TicketsDeserializer<T>(val classFromMap: (Map<String, Any?>) -> T,
                                  val property: (String, JsonElement) -> Pair<String, Any?>) : JsonDeserializer<T> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): T {
        val jsonObject = json!!.asJsonObject
        return classFromMap(createMap(jsonObject))
    }

    private fun createMap(jsonObject: JsonObject): Map<String, Any?> {

        val map = HashMap<String, Any?>()

        for ((name, element) in jsonObject.entrySet().asIterable()) {
            /*if (element is JsonNull) {
                continue
            }*/

            val field = property(name, element)
            map.put(field.first, field.second)
        }
        return map
    }
}

object GsonHelper {
    val gson: Gson = GsonBuilder()
            .registerTypeAdapter(UzResponse::class.java, UzResponseDeserializer())
            .registerTypeAdapter(UzRoute::class.java, UzRouteDeserializer())
            .registerTypeAdapter(Station::class.java, StationDeserializer())
            .create()

    fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    fun <T> fromJson(json: String, typeOfT: Type): T {
        return gson.fromJson(json, typeOfT)
    }
}

