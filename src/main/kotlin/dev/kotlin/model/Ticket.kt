package dev.kotlin.model

import com.google.gson.JsonElement
import dev.kotlin.util.TicketsDeserializer

data class Ticket(val type: String, val title: String)

class AmountOfTickets(map: Map<String, Any?>) {
    val ticket: Ticket by map
    val amount: Number by map

    override fun toString(): String {
        return """AmountOfTickets(
            ticket = $ticket,
            amount = $amount
            )
            """
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AmountOfTickets) {
            return false
        }
        return ticket == other.ticket && amount == other.amount
    }

    override fun hashCode(): Int {
        return ticket.hashCode() + amount.hashCode()
    }
}

class AmountOfTicketsDeserializer : TicketsDeserializer<AmountOfTickets>(
        classFromMap = {
            map ->
            val mapOfParams = mapOf("ticket" to Ticket(map["type"].toString(), map["title"].toString()), "amount" to map["amount"])
            AmountOfTickets(mapOfParams)
        },

        property = { name, element ->
            val field = AmountOfTicketsFields.valueOf(name.toUpperCase())
            Pair(field.propertyName, field.toObject(element))
        })

enum class AmountOfTicketsFields(val propertyName: String, val converter: (JsonElement) -> Any) {

    ID("type", { jsonElement -> jsonElement.asString }),
    TITLE("title", { jsonElement -> jsonElement.asString }),
    LETTER("type", { jsonElement -> jsonElement.asString }),
    PLACES("amount", { jsonElement -> jsonElement.asInt });

    fun toObject(jsonElement: JsonElement): Any {
        return converter.invoke(jsonElement)
    }
}