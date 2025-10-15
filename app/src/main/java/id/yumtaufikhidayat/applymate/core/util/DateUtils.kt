package id.yumtaufikhidayat.applymate.core.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Instant.toReadableString(
    pattern: String = "dd MMM yyyy, HH:mm",
    zoneId: ZoneId = ZoneId.of("Asia/Jakarta"),
    locale: Locale = Locale("id", "ID")
): String {
    return DateTimeFormatter
        .ofPattern(pattern)
        .withLocale(locale)
        .withZone(zoneId)
        .format(this)
}