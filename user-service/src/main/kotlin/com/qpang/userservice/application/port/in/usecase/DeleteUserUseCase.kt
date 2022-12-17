package com.qpang.userservice.application.port.`in`.usecase

interface DeleteUserUseCase {
    fun command(command: DeleteUserCommand): DeleteUserInfo

    data class DeleteUserCommand(
        val username: String
    )

    data class DeleteUserInfo(
        val username: String,
        val name: String
    )
}