package com.example.demo

import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository: JpaRepository<Admin, String>