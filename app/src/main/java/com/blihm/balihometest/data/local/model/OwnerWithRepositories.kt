package com.blihm.balihometest.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class OwnerWithRepositories(
    @Embedded val owner: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerId"
    )
    val repositories: List<RepositoryEntity>
) {
}