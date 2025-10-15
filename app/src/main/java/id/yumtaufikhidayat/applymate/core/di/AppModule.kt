package id.yumtaufikhidayat.applymate.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.yumtaufikhidayat.applymate.data.local.dao.ApplicationDao
import id.yumtaufikhidayat.applymate.data.local.dao.StatusHistoryDao
import id.yumtaufikhidayat.applymate.data.local.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ApplyMateDB"
        ).build()
    }

    @Provides
    @Singleton
    fun provideApplicationDao(db: AppDatabase): ApplicationDao = db.applicationDao()

    @Provides
    @Singleton
    fun provideStatusHistoryDao(db: AppDatabase): StatusHistoryDao = db.statusHistoryDao()
}