package dev.kotlin.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import java.lang.reflect.Type

data class UzResponse(var value: String = "", var error: Boolean = false, var data: String = "", var captcha: Boolean = false)

class UzResponseDeserializer : JsonDeserializer<UzResponse> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): UzResponse {
        val jsonObject = json!!.asJsonObject
        val uzResponse = UzResponse()
        for ((name, element) in jsonObject.entrySet().asIterable()) {
            if (element is JsonNull) {
                continue
            }
            when (name) {
                "value" -> uzResponse.value = element.toString()
                "error" -> uzResponse.error = element.asBoolean
                "data" -> uzResponse.data = element.asString
                "captcha" -> uzResponse.captcha = element.asBoolean
            }
        }
        return uzResponse
    }
}