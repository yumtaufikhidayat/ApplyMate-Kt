package id.yumtaufikhidayat.applymate.core.ext

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Instant.toReadableString(
    pattern: String = "EEEE, dd MMMM yyyy '•' HH:mm",
    zoneId: ZoneId = ZoneId.of("Asia/Jakarta"),
    locale: Locale = Locale("id", "ID")
): String = DateTimeFormatter
    .ofPattern(pattern)
    .withLocale(locale)
    .withZone(zoneId)
    .format(this)