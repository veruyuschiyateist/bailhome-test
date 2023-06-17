package com.blihm.balihometest.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.blihm.balihometest.data.local.db.UsersDatabase
import com.blihm.balihometest.data.local.model.UserEntity
import com.blihm.balihometest.data.network.UsersRemoteMediator
import com.blihm.balihometest.data.network.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUsersDatabase(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            "users.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUsersApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideUsersPager(
        usersDatabase: UsersDatabase,
        githubApi: GithubApi
    ): Pager<Int, UserEntity> {
        return Pager(
            config = PagingConfig(pageSize = 15, enablePlaceholders = false),
            remoteMediator = UsersRemoteMediator(
                usersDao = usersDatabase.usersDao(),
                githubApi = githubApi
            ),
            pagingSourceFactory = {
                usersDatabase.usersDao().pagingSource()
            }
        )
    }
}