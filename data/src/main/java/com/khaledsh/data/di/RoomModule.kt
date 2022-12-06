package com.khaledsh.data.di

import android.app.Application
import androidx.room.Room
import com.khaledsh.core.di.scopes.AppScope
import com.khaledsh.data.db.AppDatabase
import com.khaledsh.data.db.dao.UserDao
import dagger.Module
import dagger.Provides

private const val DB_NAME = "GITHUB_DB"

@Module
class RoomModule(application: Application) {


    private var database: AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @AppScope
    @Provides
    fun provideDatabase(): AppDatabase = database


    @AppScope
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.getUserDao()

}