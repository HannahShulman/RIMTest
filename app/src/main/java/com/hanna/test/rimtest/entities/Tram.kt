package com.hanna.test.rimtest.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "tram", strict = false)
data class Tram @JvmOverloads constructor(
    @field:Attribute(name = "destination") var destination: String ="",
    @field:Attribute(name = "dueMins") var dueMins: String= ""
){
    fun dueMinutesToDisplayString(): String {
        return when {
            dueMins.isEmpty() -> "---"
            dueMins.toInt() / 60 >= 1 -> {//over an hour
                val hours = dueMins.toInt() / 60
                val minutes = dueMins.toInt() % 60
                "${hours}h:${minutes}m"
            }
            else -> {
                val minutes = dueMins.toInt()
                "${minutes}m"
            }
        }
    }
}