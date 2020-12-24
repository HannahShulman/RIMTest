package com.hanna.test.rimtest.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(strict = false)
open class StopInfo(

    @field:Element(name = "message")
    var message: String? = null,

    @field:Attribute(name = "created")
    var created: String? = null,

    @field:Attribute(name = "stop")
    var stop: String? = null,

    @field:Attribute(name = "stopAbv")
    var stopAbv: String? = null,

    @field:ElementList(name = "direction", inline = true)
    var direction: List<Direction>? = null
)

class StopInfoResponse : StopInfo() {
    var trams = listOf<Tram>()

    fun convertToResponseFromStopInfo(
        info: StopInfo,
        dir: com.hanna.test.rimtest.datasource.Direction
    ): StopInfoResponse {
        this.message = info.message
        this.created = info.created
        this.stop = info.stop
        this.stopAbv = info.stopAbv
        this.direction = info.direction
        this.trams = direction?.filter {
            it.name == dir.direction
        }?.mapNotNull { it.tram }.orEmpty()

        return this
    }
}