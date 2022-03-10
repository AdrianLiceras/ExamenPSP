package com.example.demo
import com.google.gson.Gson
import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.spec.SecretKeySpec
import javax.persistence.ElementCollection


@RestController
class PreguntaController(private val adminRepository:AdminRepository, private val  userRepository: UserRepository,private val errorRepository: ErrorRepository,private val mensajeRepository: MensajeRepository) {

    var listaCave= listOf("0","1","2","3","4","5","6","7","8","9")



    fun crearClave():String{

        var clave=""

        for (i in 0..20)
            clave+=listaCave.random()

        return clave

    }

    @PostMapping("crearUsuario")
    fun login(@RequestBody usuario: Usuario): String {

        userRepository.findAll().forEach { it ->
            println(it)
            if (usuario.nombre==it.nombre) {
                println("Nombre comprobado")

                if (it.pass == usuario.pass) {
                    println("password comprobado")

                    return usuario.clave
                } else{
                    errorRepository.findAll().forEach{  errorRep->
                        if (errorRep.codigo==1)
                            return errorRep.toString()
                    }
                }
            }

        }
        usuario.clave=crearClave()
        userRepository.save(usuario)
        return usuario.clave

    }

    @PostMapping("crearMensaje")
    fun mensaje(@RequestBody mensaje: Mensaje):String{
       var encontrado=false
        var mensajeFin=""
        userRepository.findAll().forEach{
            if (mensaje.usuarioId==it.nombre)
                encontrado=true
        }

        if (encontrado) {
            mensajeRepository.save(mensaje)
            mensajeFin = "Success"
        }
        else{
            errorRepository.findAll().forEach{  errorRep->
                if (errorRep.codigo==2)
                    mensajeFin= errorRep.toString()
            }
        }
         return mensajeFin
    }

    @GetMapping("descargarMensajes")
    fun descarga():String{
        var descarga=""
        mensajeRepository.findAll().forEach {
            println(it.toString())
            descarga+=it.toString()
        }
        return descarga
    }
    @GetMapping("obtenerMensajesYLlaves")
    fun obtenerLlaves(@RequestBody admin: Admin):String{
      var listLaves=""
        adminRepository.findAll().forEach { adRep->

            if (admin.nombre==adRep.nombre)
                if (admin.pass==adRep.pass){
                    mensajeRepository.findAll().forEach { mensaje ->
                        listLaves+=mensaje.toString()
                        userRepository.findAll().forEach {
                            if (mensaje.usuarioId == it.nombre)
                                listLaves+=it.clave
                        }
                    }

                    return listLaves
                }else{
                    errorRepository.findAll().forEach { errorRep ->
                        if (errorRep.codigo == 3)
                            return errorRep.toString()
                    }
                }
            else
                errorRepository.findAll().forEach { errorRep ->
                    if (errorRep.codigo == 3)
                        return errorRep.toString()
                }
        }

        println(listLaves)
        return listLaves
    }

    @GetMapping("obtenerMensajesDescifrados")
    fun descifrarMsg(@RequestBody admin: Admin):String{
        var listaDescifrd=""
        var mensajeDescifrd=Mensaje("","")
        adminRepository.findAll().forEach { adRep ->
            println(admin)
            println(adRep)
            if (admin.nombre == adRep.nombre)
                if (admin.pass == adRep.pass) {
                    mensajeRepository.findAll().forEach { msg ->
                        userRepository.findAll().forEach { user ->
                            try {
                                mensajeDescifrd = Mensaje(descifrar(msg.texto, user.clave), msg.usuarioId, msg.id, msg.time)
                            }catch (throws : IllegalBlockSizeException){
                                println("Texto indescifrable")
                                return "Texto indescifrable"
                            }

                        }
                        listaDescifrd += mensajeDescifrd.toString()
                    }
                } else {
                    errorRepository.findAll().forEach { errorRep ->
                        if (errorRep.codigo == 3)
                            return errorRep.toString()
                    }
                }
            else
                errorRepository.findAll().forEach { errorRep ->
                    if (errorRep.codigo == 3)
                        return errorRep.toString()
                }

        }


        return listaDescifrd
    }

    val type = "AES/ECB/PKCS5Padding"

    private fun cifrar(textoEnString : String, llaveEnString : String) : String {
        println("Voy a cifrar: $textoEnString")
        val cipher = Cipher.getInstance(type)
        cipher.init(Cipher.ENCRYPT_MODE, getKey(llaveEnString))
        val textCifrado = cipher.doFinal(textoEnString.toByteArray(Charsets.UTF_8))
        println("Texto cifrado $textCifrado")
        val textCifradoYEncodado = Base64.getUrlEncoder().encodeToString(textCifrado)
        println("Texto cifrado y encodado $textCifradoYEncodado")
        return textCifradoYEncodado
        //return textCifrado.toString()
    }

    @Throws(BadPaddingException::class)
    private fun descifrar(textoCifradoYEncodado : String, llaveEnString : String) : String {
        println("Voy a descifrar $textoCifradoYEncodado")
        val cipher = Cipher.getInstance(type)
        cipher.init(Cipher.DECRYPT_MODE, getKey(llaveEnString))
        val textCifradoYDencodado = Base64.getUrlDecoder().decode(textoCifradoYEncodado)
        println("Texto cifrado $textCifradoYDencodado")
        val textDescifradoYDesencodado = String(cipher.doFinal(textCifradoYDencodado))
        println("Texto cifrado y desencodado $textDescifradoYDesencodado")
        return textDescifradoYDesencodado
    }

    private fun getKey(llaveEnString : String): SecretKeySpec {
        var llaveUtf8 = llaveEnString.toByteArray(Charsets.UTF_8)
        val sha = MessageDigest.getInstance("SHA-1")
        llaveUtf8 = sha.digest(llaveUtf8)
        llaveUtf8 = llaveUtf8.copyOf(16)
        return SecretKeySpec(llaveUtf8, "AES")
    }
}
