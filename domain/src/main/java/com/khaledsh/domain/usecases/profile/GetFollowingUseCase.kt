package com.khaledsh.domain.usecases.profile

import com.khaledsh.domain.repositories.UserRepository
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun invoke(params: Params) = userRepository.getFollowing(params.username)

    data class Params(val username: String)

}