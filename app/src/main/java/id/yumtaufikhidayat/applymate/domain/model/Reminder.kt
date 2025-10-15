package id.yumtaufikhidayat.applymate.domain.model

import java.time.Instant

data class Reminder(
    val id: Long = 0,
    val applicationId: Long,
    val title: String,
    val triggerAt: Instant,
    val isDone: Boolean = false,
    val type: ReminderType
)