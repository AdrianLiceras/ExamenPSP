package com.example.demo

import org.springframework.data.jpa.repository.JpaRepository

interface MensajeRepository:JpaRepository<Mensaje,Int>
