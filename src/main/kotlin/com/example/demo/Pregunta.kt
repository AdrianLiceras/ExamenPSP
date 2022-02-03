package com.example.demo

import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Pregunta(
    var pregunta:String,
    var a:String,
    var b:String,
    var c:String,
    var d:String,
    var respuestaCorrecta:String,
    ) {
    @Id
    @GeneratedValue
     var id=0

    override fun toString(): String {
        val gson = Gson()

        return gson.toJson(this)

    }

}