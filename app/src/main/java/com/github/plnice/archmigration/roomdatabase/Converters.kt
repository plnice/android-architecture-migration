package com.github.plnice.archmigration.roomdatabase

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    @TypeConverter
    fun fromString(value: String?) = value?.let { dateFormat.parse(it) }

    @TypeConverter
    fun dateToString(date: Date?) = date?.let { dateFormat.format(it) }

}
