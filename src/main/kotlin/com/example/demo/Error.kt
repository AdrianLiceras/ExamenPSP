package com.example.demo

import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Error(@Id var codigo:Int, var mensajeError:String)  {

    override fun toString(): String {
        val gson = Gson()

        return gson.toJson(this)

    }
}