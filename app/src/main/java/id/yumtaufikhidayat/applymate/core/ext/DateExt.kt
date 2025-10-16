package id.yumtaufikhidayat.applymate.core.ext

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toReadableString(): String {
    return this.format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale("id", "ID")))
}
