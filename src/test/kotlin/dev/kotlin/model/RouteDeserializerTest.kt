package dev.kotlin.model

import dev.kotlin.util.GsonHelper
import org.junit.Assert.*
import org.junit.Test
import java.time.Duration
import java.time.LocalDateTime

class RouteDeserializerTest {

    @Test
    fun testRouteDeserializerSuccess() {
        val routeJson = RouteDeserializerTest::class.java.getResource("/successful-route.json").readText()
        val route = GsonHelper.fromJson(routeJson, Route::class.java)

        val fromEndpoint = Endpoint(mapOf("station" to Station(mapOf("name" to "Кам`янець-Подільський")), "date" to LocalDateTime.of(2017, 7, 5, 4, 56, 0)))

        val toEndpoint = Endpoint(mapOf("station" to Station(mapOf("name" to "Київ-Пасажирський")), "date" to LocalDateTime.of(2017, 7, 5, 8, 28, 0)))

        val tickets = AmountOfTickets(mapOf("ticket" to Ticket("С3", "Сидячий третього класу"), "amount" to 37))
        val expected = Route(mapOf("number" to "770К", "model" to 0, "category" to 0, "travelTime" to Duration.ofMinutes(212), "from" to fromEndpoint, "to" to toEndpoint, "availableTickets" to listOf(tickets), "allowStudentDiscount" to true, "allowTransportation" to true, "allowBooking" to true))

        assertEquals(expected, route)
    }
}