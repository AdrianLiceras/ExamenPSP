package com.example.demo
import com.google.gson.Gson
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class PreguntaController(private val preguntasRepository: PreguntasRepository) {
    @GetMapping("dameUnaPregunta")
    fun getPregunta(){
        println( preguntasRepository.count())
        println("Pregunta Solicitada")

        val pregunta=Pregunta("¿Si o no","si","no","puede","quizas","si")
        preguntasRepository.save(pregunta)
        println( preguntasRepository.count())
    }
    @GetMapping("actualizarPreg/{id}/{nuevaRespuesta}")
    fun actualizarPreg(@PathVariable id:Int,@PathVariable resp:String){
        val posiblePregunta=preguntasRepository.findById(id)

        if (posiblePregunta.isPresent) {
            val pregunta = posiblePregunta.get()
            pregunta.respuestaCorrecta = resp
            preguntasRepository.save(pregunta)
        }
        preguntasRepository.findAll().forEach {
            println(it)
        }
    }
    @GetMapping("mostrar")
    fun mostrarPregunta(): MutableList<Pregunta> {
        return preguntasRepository.findAll()
    }

    @GetMapping("crear/{preg}/{opA}/{opB}/{opC}/{opD}/{resp}")
    fun crearPregunta(@PathVariable preg:String ,
                      @PathVariable opA:String ,
                      @PathVariable opB:String,
                      @PathVariable opC:String,
                      @PathVariable opD: String,
                      @PathVariable resp:String): MutableList<Pregunta> {
        val pregunta=Pregunta(preg,opA,opB,opC,opD,resp)
        preguntasRepository.save(pregunta)
        return preguntasRepository.findAll()
    }

    @GetMapping("getPregById/{pregId}")
    fun getPokemonById(@PathVariable id:Int): Pregunta {
        val preg=preguntasRepository.getById(id)
        return preg
    }

    @GetMapping("insertPreguntaBody")
    fun insertPreguntaBody(@RequestBody texto : String){

        val gson= Gson()
        val pregunta= gson.fromJson(texto,Pregunta::class.java)
        preguntasRepository.save(pregunta)
        preguntasRepository.findAll().forEach {
            println(it)
        }
        println(texto)
    }

    @GetMapping("insertPreguntaJson")
    fun insertPreguntaJson(@RequestBody pregunta: Pregunta){

        preguntasRepository.save(pregunta)
        preguntasRepository.findAll().forEach {
            println(it)
        }

    }
}