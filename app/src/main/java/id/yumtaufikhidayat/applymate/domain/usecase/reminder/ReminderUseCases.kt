package id.yumtaufikhidayat.applymate.domain.usecase.reminder

data class ReminderUseCases(
    val addReminder: AddReminderUseCase,
    val getUpcomingReminders: GetUpcomingRemindersUseCase,
    val markReminderDone: MarkReminderDoneUseCase
)