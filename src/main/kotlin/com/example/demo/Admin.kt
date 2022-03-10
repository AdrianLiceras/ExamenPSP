package com.example.demo

import com.google.gson.Gson
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Admin(@Id var nombre:String, var pass:String ) {

    @ElementCollection
    var lista= mutableListOf<String>()
    override fun toString(): String {
        val gson = Gson()

        return gson.toJson(this)

    }
}