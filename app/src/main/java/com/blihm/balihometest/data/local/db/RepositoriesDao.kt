package com.blihm.balihometest.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.blihm.balihometest.data.local.model.RepositoryEntity

@Dao
interface RepositoriesDao {

    @Upsert
    suspend fun upsertAll(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repositories WHERE ownerName LIKE :login")
    fun pagingSource(login: String): PagingSource<Int, RepositoryEntity>

    @Query("DELETE FROM repositories WHERE ownerName LIKE :login")
    suspend fun clearAll(login: String)

    @Transaction
    suspend fun clearAndUpsert(login: String, repositories: List<RepositoryEntity>) {
        clearAll(login)
        upsertAll(repositories)
    }
}