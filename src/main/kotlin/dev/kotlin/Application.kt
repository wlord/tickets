package dev.kotlin

import com.github.kittinunf.fuel.Fuel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.kotlin.model.AmountOfTickets
import dev.kotlin.model.UzResponse
import dev.kotlin.model.UzResponseDeserializer
import dev.kotlin.model.UzRoute
import dev.kotlin.util.GsonHelper

fun main(args: Array<String>) {
    Fuel.post("http://booking.uz.gov.ua/purchase/search/",
            listOf("station_id_from" to "2208536", "station_id_till" to "2200001", "date_dep" to "06.07.2017", "time_dep" to "00:00")
    ).header(mapOf("Content-Type" to "application/x-www-form-urlencoded", "Accept" to "application/json")).response { _, response, _ ->
        val uzResponse = GsonBuilder().registerTypeAdapter(UzResponse::class.java, UzResponseDeserializer()).create().fromJson(String(response.data), UzResponse::class.java)
        if (uzResponse.error) {
            print(uzResponse)
            print("Error")
        } else {
            print(uzResponse)
            val type = object : TypeToken<Collection<UzRoute>>() {}.type
            try {
                val route = GsonHelper.fromJson<Collection<UzRoute>>(uzResponse.value, type)
                print(route)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}