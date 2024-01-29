package com.example.examenmediciones.data

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun fromTimestamp(value:Long?):LocalDate?{
        return value?.let{LocalDate.ofEpochDay(it)}

    }

    @TypeConverter
    fun fromTimestamp(date:LocalDate?):Long?{
        return date?.toEpochDay()

    }

}