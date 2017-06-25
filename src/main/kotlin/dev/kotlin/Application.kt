package dev.kotlin

import com.google.gson.reflect.TypeToken
import dev.kotlin.api.UzApi
import dev.kotlin.model.RouteRequest
import dev.kotlin.model.Route
import dev.kotlin.model.Station
import dev.kotlin.model.StationRequest
import dev.kotlin.util.GsonHelper
import dev.kotlin.util.PropertiesLoader
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

val DATE_FORMAT: DateTimeFormatter = DateTimeFormatterBuilder()
        .appendValue(ChronoField.DAY_OF_MONTH, 2)
        .appendLiteral('.')
        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
        .appendLiteral('.')
        .appendValue(ChronoField.YEAR, 4)
        .toFormatter()

fun main(args: Array<String>) {

    val prop = PropertiesLoader().properties
    prop.forEach { key, value -> println("key=$key, value=$value") }

    val fromStationRequest = StationRequest(
            baseUrl = prop.getProperty("request.baseurl"),
            searchTerm = "Київ"
    )

    var from: Station
    var to: Station
    UzApi().getStation(fromStationRequest) { station ->
        from = station
    }

    val toStationRequest = StationRequest(
            baseUrl = prop.getProperty("request.baseurl"),
            searchTerm = "Київ"
    )
    UzApi().getStation(toStationRequest) { station ->
        to = station
    }

    /*val request = RouteRequest(
            baseUrl = prop.getProperty("request.baseurl"),
            from = Station(mapOf("id" to prop.getProperty("request.station.from.id").toInt())),
            to = Station(mapOf("id" to prop.getProperty("request.station.to.id").toInt())),
            departureDate = LocalDateTime.of(
                    LocalDate.parse(prop.getProperty("request.departure.date"), DATE_FORMAT),
                    LocalTime.parse(prop.getProperty("request.departure.time"))
            )
    )

    print(request)

    UzApi().getRoutes(request) { response ->
        print(response)
        val type = object : TypeToken<Collection<Route>>() {}.type
        try {
            val route = GsonHelper.fromJson<Collection<Route>>(response.value, type)
            print(route)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/
}