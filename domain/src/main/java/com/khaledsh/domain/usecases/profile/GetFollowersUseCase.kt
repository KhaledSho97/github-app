package com.khaledsh.domain.usecases.profile

import com.khaledsh.domain.repositories.UserRepository
import javax.inject.Inject

class GetFollowersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun invoke(params: Params) = userRepository.getFollowers(params.username)

    data class Params(val username: String)

}