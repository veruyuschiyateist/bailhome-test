package com.blihm.balihometest.data

import com.blihm.balihometest.data.local.model.RepositoryEntity
import com.blihm.balihometest.data.local.model.UserEntity
import com.blihm.balihometest.data.network.model.UserDto
import com.blihm.balihometest.data.network.model.UserRepositoryDto

fun UserDto.toUserEntity(): UserEntity = UserEntity(
    userId = id,
    avatarUrl = avatarUrl,
    login = login
)

fun UserRepositoryDto.toRepositoryEntity(): RepositoryEntity = RepositoryEntity(
    repId = id,
    name = name,
    fullName = fullName,
    private = private,
    description = description,
    fork = fork,
    createdAt = createdAt,
    updatedAt = updatedAt,
    gitUrl = gitUrl,
    sshUrl = sshUrl,
    cloneUrl = cloneUrl,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    forksCount = forksCount,
    visibility = visibility,
    ownerId = this.owner.id
)

