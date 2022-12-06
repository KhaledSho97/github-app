package com.khaledsh.domain.usecases.profile

import com.khaledsh.domain.models.Result
import com.khaledsh.domain.models.Success
import com.khaledsh.domain.models.User
import com.khaledsh.domain.repositories.UserRepository
import com.khaledsh.domain.usecases.base.BaseNetworkUseCase
import com.khaledsh.domain.usecases.base.UseCaseParameters
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseNetworkUseCase<User, GetUserUseCase.Params>() {

    override suspend fun FlowCollector<Result<User>>.run(params: Params) {
        // 1. Fetch temp user from local db
        val dbUser = userRepository.getUserFromLocalDb(params.username)
        if(dbUser != null){
            emit(Success(dbUser))
        }

        // 2. Fetch & emit remote user
        emit(userRepository.getUser(params.username))
    }

    data class Params(val username: String) : UseCaseParameters

}