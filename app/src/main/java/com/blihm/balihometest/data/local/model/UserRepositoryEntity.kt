package com.blihm.balihometest.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "repositories")
data class UserRepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val fullName: String,
    val private: Boolean,
    val description: String,
    val fork: Boolean,
    val createdAt: Date,
    val updatedAt: Date,
    val gitUrl: String,
    val sshUrl: String,
    val cloneUrl: String,
    val stargazersCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val visibility: String
)
