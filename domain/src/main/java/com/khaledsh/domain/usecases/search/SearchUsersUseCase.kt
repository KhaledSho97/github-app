package com.khaledsh.domain.usecases.search

import com.khaledsh.domain.repositories.UserRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val userRepository: UserRepository) {


    suspend fun run(params: Params) = userRepository.searchUsers(params.query)


    data class Params(val query: String)

}