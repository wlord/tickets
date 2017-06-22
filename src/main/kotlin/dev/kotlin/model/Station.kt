package dev.kotlin.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class Station(map: Map<String, Any?>) {
    val id: Number by map
    val name: String by map
}

class StationDeserializer : JsonDeserializer<Station> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Station {

    }

}