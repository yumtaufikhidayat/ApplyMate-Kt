package id.yumtaufikhidayat.applymate.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.yumtaufikhidayat.applymate.domain.repository.ApplicationRepository
import id.yumtaufikhidayat.applymate.domain.repository.ProfileRepository
import id.yumtaufikhidayat.applymate.domain.repository.ReminderRepository
import id.yumtaufikhidayat.applymate.domain.repository.StatusHistoryRepository
import id.yumtaufikhidayat.applymate.domain.usecase.application.AddApplicationUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.application.ApplicationUseCases
import id.yumtaufikhidayat.applymate.domain.usecase.application.DeleteApplicationUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.application.GetApplicationByIdUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.application.GetApplicationsUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.application.UpdateApplicationStatusUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.application.UpdateApplicationUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.profile.CalculateProfileStrengthUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.profile.GetProfileUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.profile.ProfileUseCases
import id.yumtaufikhidayat.applymate.domain.usecase.profile.SaveProfileUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.reminder.AddReminderUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.reminder.GetUpcomingRemindersUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.reminder.MarkReminderDoneUseCase
import id.yumtaufikhidayat.applymate.domain.usecase.reminder.ReminderUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideApplicationUseCases(
        appRepo: ApplicationRepository,
        historyRepo: StatusHistoryRepository
    ): ApplicationUseCases = ApplicationUseCases(
        addApplication = AddApplicationUseCase(appRepo),
        getApplications = GetApplicationsUseCase(appRepo),
        getApplicationById = GetApplicationByIdUseCase(appRepo),
        updateApplicationStatus = UpdateApplicationStatusUseCase(appRepo, historyRepo),
        updateApplication = UpdateApplicationUseCase(appRepo),
        deleteApplication = DeleteApplicationUseCase(appRepo)
    )

    @Provides
    @Singleton
    fun provideReminderUseCases(
        reminderRepo: ReminderRepository
    ): ReminderUseCases = ReminderUseCases(
        addReminder = AddReminderUseCase(reminderRepo),
        getUpcomingReminders = GetUpcomingRemindersUseCase(reminderRepo),
        markReminderDone = MarkReminderDoneUseCase(reminderRepo)
    )

    @Provides
    @Singleton
    fun provideProfileuseCases(
        profileRepo: ProfileRepository
    ): ProfileUseCases = ProfileUseCases(
        getProfile = GetProfileUseCase(profileRepo),
        saveProfile = SaveProfileUseCase(profileRepo),
        calculateProfileStrength = CalculateProfileStrengthUseCase()
    )
}