package com.example.demo

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<Usuario, String>
