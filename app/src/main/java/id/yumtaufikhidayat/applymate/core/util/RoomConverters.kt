package id.yumtaufikhidayat.applymate.core.util

import androidx.room.TypeConverter
import java.time.Instant

class RoomConverters {
    @TypeConverter
    fun fromInstant(value: Instant?): Long? = value?.toEpochMilli()

    @TypeConverter
    fun toInstant(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }
}