package dev.kotlin

import com.github.kittinunf.fuel.Fuel
import com.google.gson.GsonBuilder
import dev.kotlin.model.UzResponse
import dev.kotlin.model.UzResponseDeserializer

fun main(args: Array<String>) {
    Fuel.post("http://booking.uz.gov.ua/purchase/search/",
            listOf("station_id_from" to "2208536", "station_id_till" to "2200001", "date_dep" to "06.07.2017", "time_dep" to "00:00")
    ).header(mapOf("Content-Type" to "application/x-www-form-urlencoded", "Accept" to "application/json" )).response { _, response, _ ->
        val uzResponse = GsonBuilder().registerTypeAdapter(UzResponse::class.java, UzResponseDeserializer()).create().fromJson(String(response.data), UzResponse::class.java)
        if (uzResponse.error) {
            print(uzResponse)
            print("Error")
        } else {
            print(uzResponse)
        }
    }
}