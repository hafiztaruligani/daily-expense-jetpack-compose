package com.dailyexpenses.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dailyexpenses.data.local.room.entity.ExpensesEntity
import com.dailyexpenses.data.local.room.entity.ExpensesTagCrossRef
import com.dailyexpenses.data.local.room.entity.TagEntity

@Database(
    entities = [
        TagEntity::class,
        ExpensesEntity::class,
        ExpensesTagCrossRef::class
    ],
    version = 1
)
// @TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tagDao(): TagDao
    abstract fun expensesDao(): ExpensesDao

    companion object {
        fun getDatabaseInstance(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "expenses_database"
        ).build()
    }

}
