package com.example.demo

import com.google.gson.Gson
import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import kotlin.random.Random

@Entity
data class Mensaje(var texto:String, var usuarioId:String , var id: Int =0,var time:Timestamp= Timestamp(Random.nextLong())
) {
    @Id
    @GeneratedValue
    var idMn=0


    override fun toString(): String {
        val gson = Gson()

        return gson.toJson(this)

    }

}