package com.qpang.userservice.application.port.`in`.usecase

interface UpdateUserInfoUseCase {
    fun command(command: UpdateUserCommand): UpdateUserInfo

    data class UpdateUserCommand(
        val id: String,
        val name: String
    )

    data class UpdateUserInfo(
        val id: String,
        val username: String,
        val name: String
    )
}