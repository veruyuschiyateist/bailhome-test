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

    @Query("SELECT * FROM repositories")
    fun pagingSource(): PagingSource<Int, RepositoryEntity>

    @Query("DELETE FROM repositories")
    suspend fun clearAll()

    @Transaction
    suspend fun clearAndUpsert(repositories: List<RepositoryEntity>) {
        clearAll()
        upsertAll(repositories)
    }
}