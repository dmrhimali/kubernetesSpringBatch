package com.example.springbatchdemo.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
data class User(
        @Id
        val id: Long,
        @NotNull
        val name: String,
        var dept: String?
)