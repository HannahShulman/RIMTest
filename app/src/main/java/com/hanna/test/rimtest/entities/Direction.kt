package com.hanna.test.rimtest.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "direction")
data class Direction @JvmOverloads constructor(
    @field:Element(name = "tram")
    var tram: Tram? = null,

    @field:Attribute(name = "name")
    var name: String? = null
)