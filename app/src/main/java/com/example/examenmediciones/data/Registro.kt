package com.example.examenmediciones.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Registro(
    @PrimaryKey(autoGenerate= true) var id:Long? = null,
    var fecha:LocalDate,
    var medidor: Int,
    var tiporegistro:String,
)