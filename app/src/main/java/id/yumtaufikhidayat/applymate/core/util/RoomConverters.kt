package id.yumtaufikhidayat.applymate.core.util

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RoomConverters {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    @TypeConverter
    fun fromInstant(value: Instant?): Long? = value?.toEpochMilli()

    @TypeConverter
    fun toInstant(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }

    // LocalDate <-> String
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.format(formatter)

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it, formatter) }
}