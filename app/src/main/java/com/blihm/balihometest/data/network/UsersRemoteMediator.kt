package com.blihm.balihometest.data.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import coil.network.HttpException
import com.blihm.balihometest.data.local.db.UsersDao
import com.blihm.balihometest.data.local.model.UserEntity
import com.blihm.balihometest.data.network.api.GithubApi
import com.blihm.balihometest.data.toUserEntity
import okio.IOException

@OptIn(ExperimentalPagingApi::class)
class UsersRemoteMediator(
    private val usersDao: UsersDao,
    private val githubApi: GithubApi
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        lastItem.id
                    }
                }
            }

            val users = githubApi.getUsers(
                since = loadKey,
                perPage = state.config.pageSize
            )

            if (loadType == LoadType.REFRESH) {
                usersDao.clearAndUpsert(users = users.map { it.toUserEntity() })
            } else {
                usersDao.upsertAll(users = users.map { it.toUserEntity() })
            }

            MediatorResult.Success(
                endOfPaginationReached = users.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}