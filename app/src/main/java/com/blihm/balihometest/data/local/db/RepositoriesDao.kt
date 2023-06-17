package com.blihm.balihometest.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.blihm.balihometest.data.local.model.UserRepositoryEntity

@Dao
interface RepositoriesDao {

    @Upsert
    suspend fun upsertAll(repositories: List<UserRepositoryEntity>)

    @Query("SELECT * FROM repositories")
    fun pagingSource(): PagingSource<Int, UserRepositoryEntity>

    @Query("DELETE FROM repositories")
    suspend fun clearAll()

    @Transaction
    suspend fun clearAndUpsert(repositories: List<UserRepositoryEntity>) {
        clearAll()
        upsertAll(repositories)
    }
}