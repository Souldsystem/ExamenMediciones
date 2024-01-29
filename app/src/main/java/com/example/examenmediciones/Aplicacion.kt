package com.example.examenmediciones

import android.app.Application
import androidx.room.Room
import com.example.examenmediciones.data.BaseDatos

class Aplicacion : Application() {

    val db by lazy {
        Room.databaseBuilder(this, BaseDatos::class.java, "registros.db").build()}

    val registroDao by lazy { db.registroDao() }

}