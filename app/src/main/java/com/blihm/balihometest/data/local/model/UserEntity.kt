package com.blihm.balihometest.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val userId: Int,
    val avatarUrl: String,
    val login: String,
)