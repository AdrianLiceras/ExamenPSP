package com.example.demo

import org.springframework.data.jpa.repository.JpaRepository

interface ErrorRepository :JpaRepository<Error,Int>{
}