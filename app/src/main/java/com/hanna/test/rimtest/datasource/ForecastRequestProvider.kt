package com.hanna.test.rimtest.datasource

import com.google.gson.annotations.SerializedName
import java.util.*


data class RequestInfo(val stopCode: String, val direction: Direction)
object ForecastRequestProvider {

    fun requestInfo(
        midNight: Boolean = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 12
    ): RequestInfo {
        val stopCode = "mar".takeUnless { midNight } ?: "sti"
        val direction = Direction.OUTBOUND.takeUnless { midNight } ?: Direction.INBOUND
        return RequestInfo(stopCode, direction)
    }

}

enum class Direction(val direction: String) {
    @SerializedName("Inbound")
    INBOUND("Inbound"),

    @SerializedName("Outbound")
    OUTBOUND("Outbound")
}