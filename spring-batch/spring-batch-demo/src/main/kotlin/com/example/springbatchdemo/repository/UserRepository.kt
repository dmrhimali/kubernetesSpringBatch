package com.example.springbatchdemo.repository

import com.example.springbatchdemo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User?, Int?>