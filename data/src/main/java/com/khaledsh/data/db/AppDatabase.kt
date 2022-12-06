package com.khaledsh.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khaledsh.data.db.dao.RemoteKeyDao
import com.khaledsh.data.db.dao.UserDao
import com.khaledsh.data.db.entity.RemoteKey
import com.khaledsh.data.db.entity.UserEntity

@Database(
    version = 1, entities = [
        UserEntity::class,
        RemoteKey::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getRemoteKeyDao(): RemoteKeyDao

}