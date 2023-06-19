package com.blihm.balihometest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blihm.balihometest.data.local.model.RepositoryEntity
import com.blihm.balihometest.data.local.model.UserEntity

@Database(
    entities = [UserEntity::class, RepositoryEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    abstract fun repositoriesDao(): RepositoriesDao
}