package com.example.retrofitwithroom.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitwithroom.dao.EmployeeDao
import com.example.retrofitwithroom.model.Employee
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleRoom {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "employee"
        ).build()
    }

    @Provides
    fun provideItemDao(database: AppDatabase): EmployeeDao {
        return database.employeeDao()
    }
}