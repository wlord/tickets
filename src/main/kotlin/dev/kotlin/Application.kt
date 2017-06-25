package dev.kotlin

import com.github.kittinunf.fuel.Fuel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.kotlin.model.Response
import dev.kotlin.model.ResponseDeserializer
import dev.kotlin.model.Route
import dev.kotlin.util.GsonHelper

fun main(args: Array<String>) {
    Fuel.post("http://booking.uz.gov.ua/purchase/search/",
            listOf("station_id_from" to "2200270", "station_id_till" to "220001", "date_dep" to "05.07.2017", "time_dep" to "00:00")
    ).header(mapOf("Content-Type" to "application/x-www-form-urlencoded", "Accept" to "application/json")).response { _, response, _ ->
        val uzResponse = GsonBuilder().registerTypeAdapter(Response::class.java, ResponseDeserializer()).create().fromJson(String(response.data), Response::class.java)
        if (uzResponse.error) {
            print(uzResponse)
            print("Error")
        } else {
            print(uzResponse)
            val type = object : TypeToken<Collection<Route>>() {}.type
            try {
                val route = GsonHelper.fromJson<Collection<Route>>(uzResponse.value, type)
                print(route)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}