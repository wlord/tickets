package dev.kotlin

import com.google.gson.reflect.TypeToken
import dev.kotlin.api.UzApi
import dev.kotlin.model.Route
import dev.kotlin.model.RouteRequest
import dev.kotlin.model.Station
import dev.kotlin.model.StationRequest
import dev.kotlin.util.Configuration
import dev.kotlin.util.GsonHelper
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

val DATE_FORMAT: DateTimeFormatter = DateTimeFormatterBuilder()
        .appendValue(ChronoField.DAY_OF_MONTH, 2)
        .appendLiteral('.')
        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
        .appendLiteral('.')
        .appendValue(ChronoField.YEAR, 4)
        .toFormatter()

fun main(args: Array<String>) {

    val config = Configuration()
    val uzApi = UzApi()

    val from = getStation(config.fromStation(), config.baseUrl() + "station/", uzApi)
    val to = getStation(config.toStation(), config.baseUrl() + "station/", uzApi)

    val request = RouteRequest(config, from, to)

    print(request)

    Timer().schedule(object : TimerTask() {
        override fun run() {
            uzApi.getRoutes(request) { response ->
                print(response)
                val type = object : TypeToken<Collection<Route>>() {}.type
                try {
                    val route = GsonHelper.fromJson<Collection<Route>>(response.value, type)
                    print(route)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }, 0, 30000)
}

private fun getStation(stationFromConfig: Station, url: String, uzApi: UzApi): Station {
    var stationToReturn = stationFromConfig
    if (stationFromConfig.id.toInt() != 0) {
        val fromStationRequest = StationRequest(
                url = url,
                searchTerm = stationFromConfig.name
        )
        uzApi.getStation(fromStationRequest) { station ->
            stationToReturn = station
        }
    }
    return stationToReturn
}