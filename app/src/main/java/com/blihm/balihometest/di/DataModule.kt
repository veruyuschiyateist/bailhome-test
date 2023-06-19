package com.blihm.balihometest.di

import android.content.Context
import androidx.room.Room
import com.blihm.balihometest.data.local.db.Converters
import com.blihm.balihometest.data.local.db.RepositoriesDao
import com.blihm.balihometest.data.local.db.UsersDao
import com.blihm.balihometest.data.local.db.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUsersDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "users.db"
        )
            .addTypeConverter(Converters())
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersDao(database: UsersDatabase): UsersDao = database.usersDao()

    @Provides
    @Singleton
    fun provideRepositoriesDao(database: UsersDatabase): RepositoriesDao =
        database.repositoriesDao()
}