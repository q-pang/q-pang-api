package com.qpang.userservice.application.port.`in`.usecase

interface GetUserUseCase {
    fun command(command: GetUserCommand): GetUserInfo

    data class GetUserCommand(
        val id: String
    )

    data class GetUserInfo(
        val id: String,
        val username: String,
        val name: String
    )
}