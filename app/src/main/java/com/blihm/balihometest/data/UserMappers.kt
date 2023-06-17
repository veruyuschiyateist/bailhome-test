package com.blihm.balihometest.data

import com.blihm.balihometest.data.local.model.UserEntity
import com.blihm.balihometest.data.network.model.UserDto

fun UserDto.toUserEntity(): UserEntity = UserEntity(
    id = id,
    avatarUrl = avatarUrl,
    login = login
)

