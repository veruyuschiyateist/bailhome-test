package com.blihm.balihometest.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/*
    Column fields were made temporary mutable to avoid kapt plugin bugs
 */
@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey(autoGenerate = false) var repId: Int,
    var ownerId: Int,
    var name: String,
    var fullName: String,
    var private: Boolean,
    var description: String?,
    var fork: Boolean,
    var createdAt: Date,
    var updatedAt: Date,
    var gitUrl: String,
    var sshUrl: String,
    var cloneUrl: String,
    var stargazersCount: Int,
    var watchersCount: Int,
    var forksCount: Int,
    var visibility: String
) {
    constructor() : this(0, 0, "", "", false, "", false, Date(), Date(), "", "", "", 0, 0, 0, "")
}
