package com.blihm.balihometest.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.blihm.balihometest.data.local.db.UsersDatabase
import com.blihm.balihometest.data.local.model.UserEntity
import com.blihm.balihometest.data.UsersRemoteMediator
import com.blihm.balihometest.data.local.db.UsersDao
import com.blihm.balihometest.data.network.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideUsersPager(
        usersDao: UsersDao,
        githubApi: GithubApi
    ): Pager<Int, UserEntity> {
        return Pager(
            config = PagingConfig(pageSize = 15, enablePlaceholders = false),
            remoteMediator = UsersRemoteMediator(
                usersDao = usersDao,
                githubApi = githubApi
            ),
            pagingSourceFactory = {
                usersDao.pagingSource()
            }
        )
    }
}