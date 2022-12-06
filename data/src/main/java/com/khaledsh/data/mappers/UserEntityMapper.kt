package com.khaledsh.data.mappers

import com.khaledsh.data.db.entity.UserEntity
import com.khaledsh.data.models.UserDTO
import com.khaledsh.domain.models.UserType
import javax.inject.Inject

class UserEntityMapper @Inject constructor() {

    fun map(dto: UserDTO, userType: UserType, parentUsername: String?): UserEntity = UserEntity(
        dbId = 0,
        id = dto.id,
        userType = userType,
        username = dto.login,
        avatarUrl = dto.avatarUrl,
        bio = dto.bio,
        followers = dto.followers,
        following = dto.following,
        name = dto.name,
        repos = dto.repos,
        parentUsername = parentUsername
    )

    fun mapList(
        dtos: List<UserDTO>,
        userType: UserType,
        parentUsername: String?
    ): List<UserEntity> = dtos.map { map(it, userType, parentUsername) }

}