package com.blihm.balihometest.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.blihm.balihometest.data.local.model.OwnerWithRepositories
import com.blihm.balihometest.data.local.model.UserEntity

@Dao
interface UsersDao {

    @Upsert
    suspend fun upsertAll(users: List<UserEntity>)

    @Query("SELECT * FROM users")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Transaction
    @Query("SELECT * FROM users WHERE login LIKE :login")
    fun repositoriesPagingSource(login: String): PagingSource<Int, OwnerWithRepositories>

    @Transaction
    suspend fun clearAndUpsert(users: List<UserEntity>) {
        clearAll()
        upsertAll(users)
    }
}