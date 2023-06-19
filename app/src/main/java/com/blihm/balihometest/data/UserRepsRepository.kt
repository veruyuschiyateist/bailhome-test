package com.blihm.balihometest.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blihm.balihometest.data.local.db.RepositoriesDao
import com.blihm.balihometest.data.local.model.RepositoryEntity
import com.blihm.balihometest.data.network.api.GithubApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepsRepository {

    fun getRepositories(login: String): Flow<PagingData<RepositoryEntity>>
}

@Singleton
class PagingUserRepsRepository @Inject constructor(
    private val repositoriesDao: RepositoriesDao,
    private val githubApi: GithubApi
) : UserRepsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRepositories(login: String): Flow<PagingData<RepositoryEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            remoteMediator = RepositoriesRemoteMediator(
                githubApi = githubApi,
                repositoriesDao = repositoriesDao,
                userLogin = login
            ),
            pagingSourceFactory = {
                repositoriesDao.pagingSource(login)
            }
        ).flow
    }

}

