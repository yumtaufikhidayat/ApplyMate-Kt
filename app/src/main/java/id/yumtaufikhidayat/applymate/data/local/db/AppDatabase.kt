package id.yumtaufikhidayat.applymate.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import id.yumtaufikhidayat.applymate.core.util.RoomConverters

import id.yumtaufikhidayat.applymate.data.local.dao.ApplicationDao
import id.yumtaufikhidayat.applymate.data.local.dao.ProfileDao
import id.yumtaufikhidayat.applymate.data.local.dao.ReminderDao
import id.yumtaufikhidayat.applymate.data.local.dao.StatusHistoryDao
import id.yumtaufikhidayat.applymate.data.local.entity.ApplicationEntity
import id.yumtaufikhidayat.applymate.data.local.entity.ProfileEntity
import id.yumtaufikhidayat.applymate.data.local.entity.ReminderEntity
import id.yumtaufikhidayat.applymate.data.local.entity.StatusHistoryEntity

@Database(
    entities = [
        ApplicationEntity::class,
        ReminderEntity::class,
        ProfileEntity::class,
        StatusHistoryEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun applicationDao(): ApplicationDao
    abstract fun reminderDao(): ReminderDao
    abstract fun profileDao(): ProfileDao
    abstract fun statusHistoryDao(): StatusHistoryDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE applications ADD COLUMN interviewDate TEXT")
                database.execSQL("ALTER TABLE applications ADD COLUMN interviewLink TEXT")
            }
        }
    }
}