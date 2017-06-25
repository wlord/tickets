package dev.kotlin.model

import dev.kotlin.util.GsonHelper
import org.junit.Assert.*
import org.junit.Test

class ResponseDeserializerTest {

    @Test
    fun testResponseDeserializerSuccess() {
        val responseJson = ResponseDeserializerTest::class.java.getResource("/successful-response.json").readText()
        val response = GsonHelper.fromJson(responseJson, Response::class.java)

        val expected = Response(mapOf("value" to "[{\"num\":\"770К\",\"model\":0,\"category\":0,\"travel_time\":\"3:32\"," +
                "\"from\":{\"station\":\"Кам`янець-Подільський\",\"date\":1499219760,\"src_date\":\"2017-07-05 04:56:00\"}," +
                "\"till\":{\"station\":\"Київ-Пасажирський\",\"date\":1499232480,\"src_date\":\"2017-07-05 08:28:00\"}," +
                "\"types\":[{\"id\":\"С3\",\"title\":\"Сидячий третього класу\",\"letter\":\"С3\",\"places\":37}],\"allow_stud\":1," +
                "\"allow_transportation\":1,\"allow_booking\":1},{\"num\":\"146Ш\",\"model\":0,\"category\":0,\"travel_time\":\"4:06\"," +
                "\"from\":{\"station\":\"Ізмаїл\",\"date\":1499223180,\"src_date\":\"2017-07-05 05:53:00\"},\"till\":{\"station\"" +
                ":\"Київ-Пасажирський\",\"date\":1499237940,\"src_date\":\"2017-07-05 09:59:00\"},\"types\":[{\"id\":\"К\"," +
                "\"title\":\"Купе\",\"letter\":\"К\",\"places\":1},{\"id\":\"П\",\"title\":\"Плацкарт\",\"letter\":\"П\",\"places\":8}]," +
                "\"allow_stud\":1,\"allow_transportation\":1,\"allow_booking\":1}]", "error" to false, "data" to "", "captcha" to false))

        assertEquals(expected, response)
    }

    @Test
    fun testResponseDeserializerError() {
        val responseJson = ResponseDeserializerTest::class.java.getResource("/error-response.json").readText()
        val response = GsonHelper.fromJson(responseJson, Response::class.java)

        val expected = Response(mapOf("value" to "Сервіс тимчасово недоступний. Приносимо вибачення за доставлені незручності.", "error" to true,
                "data" to "", "captcha" to false))

        assertEquals(expected, response)
    }
}