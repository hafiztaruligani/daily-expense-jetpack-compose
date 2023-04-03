package com.dailyexpenses.di
import android.content.Context
import com.dailyexpenses.data.local.room.AppDatabase
import com.dailyexpenses.data.local.room.ExpensesDao
import com.dailyexpenses.data.local.room.TagDao
import com.dailyexpenses.data.repository.ExpensesRepositoryImpl
import com.dailyexpenses.data.repository.TagRepositoryImpl
import com.dailyexpenses.domain.repository.ExpensesRepository
import com.dailyexpenses.domain.repository.TagRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun appDatabase(
        @ApplicationContext context: Context
    ) = AppDatabase.getDatabaseInstance(context)

    @Provides
    @Singleton
    fun tagDao(
        appDatabase: AppDatabase
    ) = appDatabase.tagDao()

    @Provides
    @Singleton
    fun tagRepository(
        tagDao: TagDao
    ): TagRepository = TagRepositoryImpl(tagDao)

    @Provides
    @Singleton
    fun expensesDao(
        appDatabase: AppDatabase
    ) = appDatabase.expensesDao()

    @Provides
    @Singleton
    fun expensesRepository(
        expensesDao: ExpensesDao
    ): ExpensesRepository = ExpensesRepositoryImpl(expensesDao)


    @Provides
    @Singleton
    fun calendar() = Calendar.getInstance()

}
