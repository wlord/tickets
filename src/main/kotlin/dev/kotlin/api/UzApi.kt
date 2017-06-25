package dev.kotlin.api

import com.github.kittinunf.fuel.Fuel
import com.google.gson.reflect.TypeToken
import dev.kotlin.DATE_FORMAT
import dev.kotlin.model.Response
import dev.kotlin.model.RouteRequest
import dev.kotlin.model.Station
import dev.kotlin.model.StationRequest
import dev.kotlin.util.GsonHelper
import java.io.Serializable

class UzApi {

    fun getRoutes(routeRequest: RouteRequest, callback: (Response) -> Unit) {
        Fuel.post(routeRequest.baseUrl + "search/", paramsFromRequest(routeRequest))
                .header(mapOf("Content-Type" to "application/x-www-form-urlencoded", "Accept" to "application/json"))
                .response { _, response, _ ->
                    val uzResponse = GsonHelper.fromJson(String(response.data), Response::class.java)
                    if (uzResponse.error) {
                        print(uzResponse)
                        print("Error")
                    } else {
                        callback.invoke(uzResponse)
                    }
                }
    }

    fun getStation(stationRequest: StationRequest, callback: (Station) -> Unit) {
        Fuel.get(stationRequest.baseUrl + "station/", listOf("term" to stationRequest.searchTerm))
                .response {_, response, _ ->
                    print(response.data)
                    try {
                        val type = object : TypeToken<Collection<Station>>() {}.type
                        val stationJson = GsonHelper.fromJson<Collection<Station>>(String(response.data), type)
                        callback.invoke(stationJson.first())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
    }

    private fun paramsFromRequest(routeRequest: RouteRequest): List<Pair<String, Serializable>> {
        return listOf(
                "station_id_from" to routeRequest.from.id,
                "station_id_till" to routeRequest.to.id,
                "date_dep" to routeRequest.departureDate.format(DATE_FORMAT),
                "time_dep" to routeRequest.departureDate.toLocalTime().toString()
        )
    }
}