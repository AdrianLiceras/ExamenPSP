package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Database {

    @Bean
    fun initDatabase(adminRepository: AdminRepository,mensajeRepository: MensajeRepository,userRepository: UserRepository,errorRepository: ErrorRepository): CommandLineRunner{
        return CommandLineRunner{
            adminRepository.save(Admin("DAM2","123456"))
            errorRepository.save(Error(1, "Contraseña inválida"))
            errorRepository.save(Error(2, "Usuario inexistente"))
            errorRepository.save(Error(3, "Contraseña de administrador inválida"))
        }
    }

}