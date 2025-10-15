package id.yumtaufikhidayat.applymate.data.mapper

import id.yumtaufikhidayat.applymate.data.local.entity.ReminderEntity
import id.yumtaufikhidayat.applymate.domain.model.Reminder
import id.yumtaufikhidayat.applymate.domain.model.ReminderType

fun ReminderEntity.toDomain() = Reminder(
    id = id,
    applicationId = applicationId,
    title = title,
    triggerAt = triggerAt,
    isDone = isDone,
    type = ReminderType.valueOf(type)
)

fun Reminder.toEntity() = ReminderEntity(
    id = id,
    applicationId = applicationId,
    title = title,
    triggerAt = triggerAt,
    isDone = isDone,
    type = type.name
)