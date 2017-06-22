package dev.kotlin.model

class Ticket(map: Map<String, Any?>) {
    val type: String by map
    val title: String by map
}

data class AmountOfTickets(val ticket: Ticket, val amount: Number)