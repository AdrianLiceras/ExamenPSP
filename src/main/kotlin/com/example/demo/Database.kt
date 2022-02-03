package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Database {

    @Bean
    fun initDatabase(preguntasRepository: PreguntasRepository): CommandLineRunner{
        return CommandLineRunner{
            println("Base de datos creandose")
            val listaPreguntas = listOf(
                Pregunta("¿De que tipo es Charmander?","Lucha","Fuego","Acero","Hielo","Fuego"),
                Pregunta("¿De que tipo es Mudkip?","Tierra","Fuego","Agua","Veneno","Agua"),
                Pregunta("¿Cual es el primer juego de Pokemon?","Rojo","Amarillo","Verde","Rubi","Rojo"),
                Pregunta("¿Cual de estos Pokemon es único en su rama evolutiva?","Lucario","Pikachu","Spiritom","Chansei","Spiritom"),
                Pregunta("¿Que pokemon tiene las mismas estadisticas base que charizard en todas sus fases evolutivas?","Typhlosion","Dragonite","Evee","Magmar","Typhlosion"),
                Pregunta("¿Que pokemon es abandonado por el rival de Ash en Pokemon Diamante y Perla?","Charmander","Chimchar","Pikachu","Keldeo","Charmander"),
                Pregunta("¿Cuantas medallas hay que conseguir para entrar a la Liga Pokemon?","5","10","8","20","8"),
                Pregunta("¿Como se llaman los pokemon que vienen de otra dimension en Pokemon Sol y Luna?","Visitantes","Ultraentes","Pokeastros","Legendarios","Ultraentes") ,
                Pregunta("¿De que tipo es Piplup?","Agua","Fuego","Acero","Psiquico","Agua"),
                Pregunta("¿De que tipo es Charizard?","Fuego","Fuego-Volador","Dragon","Volador","Fuego-Volador")
            )
            listaPreguntas.forEach {
                println("${it.pregunta}, ${it.id} ")
            }
            listaPreguntas.forEach {
                preguntasRepository.save(it)
            }
            preguntasRepository.findAll().forEach {
                println(it)
            }
            println("Base de datos creada")
        }
    }

}