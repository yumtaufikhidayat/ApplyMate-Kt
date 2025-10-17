package id.yumtaufikhidayat.applymate.core.ext

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Instant.toReadableString(
    pattern: String = "EEEE, dd MMMM yyyy, HH:mm",
    zoneId: ZoneId = ZoneId.of("Asia/Jakarta"),
    locale: Locale = Locale("id", "ID")
): String {
    return DateTimeFormatter
        .ofPattern(pattern)
        .withLocale(locale)
        .withZone(zoneId)
        .format(this)
}

fun LocalDate.toReadableString(): String = this.format(
    DateTimeFormatter.ofPattern(
        "EEEE, dd MMM yyyy",
        Locale(
            "id",
            "ID"
        )
    )
)