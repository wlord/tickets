package dev.kotlin.util

import com.google.gson.*
import dev.kotlin.model.*
import java.lang.reflect.Type

abstract class TicketsDeserializer<T>(val classFromMap: (Map<String, Any?>) -> T,
                                  val property: (String, JsonElement) -> Pair<String, Any?>) : JsonDeserializer<T> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): T {
        val jsonObject = json!!.asJsonObject
        return classFromMap(createMap(jsonObject))
    }

    private fun createMap(jsonObject: JsonObject): Map<String, Any?> {
        val map = HashMap<String, Any?>()

        for ((name, element) in jsonObject.entrySet().asIterable()) {
            val field = property(name, element)
            map.put(field.first, field.second)
        }
        return map
    }
}

object GsonHelper {
    val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Response::class.java, ResponseDeserializer())
            .registerTypeAdapter(Route::class.java, RouteDeserializer())
            .registerTypeAdapter(Endpoint::class.java, EndpointDeserializer())
            .registerTypeAdapter(AmountOfTickets::class.java, AmountOfTicketsDeserializer())
            .create()

    fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    fun <T> fromJson(json: String, typeOfT: Type): T {
        return gson.fromJson(json, typeOfT)
    }
}

