package com.blihm.balihometest.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.blihm.balihometest.data.local.db.RepositoriesDao
import com.blihm.balihometest.data.local.model.RepositoryEntity
import com.blihm.balihometest.data.network.api.GithubApi
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class RepositoriesRemoteMediator(
    private val repositoriesDao: RepositoriesDao,
    private val githubApi: GithubApi,
    private val userLogin: String,
) : RemoteMediator<Int, RepositoryEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        lastItem.repId
                    }
                }
            }

            val repositories = githubApi.getUserRepositories(
                login = userLogin,
                perPage = state.config.pageSize,
                page = loadKey
            )

            if (loadType == LoadType.REFRESH) {
                repositoriesDao.clearAndUpsert(
                    userLogin,
                    repositories = repositories.map { it.toRepositoryEntity() })
            } else {
                repositoriesDao.upsertAll(repositories = repositories.map { it.toRepositoryEntity() })
            }

            MediatorResult.Success(
                endOfPaginationReached = repositories.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}