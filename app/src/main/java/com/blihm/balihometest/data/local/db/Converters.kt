package com.blihm.balihometest.data.local.db

import androidx.room.TypeConverter
import java.sql.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}