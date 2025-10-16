package id.yumtaufikhidayat.applymate.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.yumtaufikhidayat.applymate.data.repository.ApplicationRepositoryImpl
import id.yumtaufikhidayat.applymate.data.repository.ProfileRepositoryImpl
import id.yumtaufikhidayat.applymate.data.repository.ReminderRepositoryImpl
import id.yumtaufikhidayat.applymate.data.repository.StatusHistoryRepositoryImpl
import id.yumtaufikhidayat.applymate.domain.repository.ApplicationRepository
import id.yumtaufikhidayat.applymate.domain.repository.ProfileRepository
import id.yumtaufikhidayat.applymate.domain.repository.ReminderRepository
import id.yumtaufikhidayat.applymate.domain.repository.StatusHistoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindApplicationRepository(
        impl: ApplicationRepositoryImpl
    ): ApplicationRepository

    @Binds
    @Singleton
    abstract fun bindReminderRepository(
        impl: ReminderRepositoryImpl
    ): ReminderRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindStatusHistoryRepository(
        impl: StatusHistoryRepositoryImpl
    ): StatusHistoryRepository
}