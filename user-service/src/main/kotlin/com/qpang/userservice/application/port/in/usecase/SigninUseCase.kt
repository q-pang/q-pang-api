package com.qpang.userservice.application.port.`in`.usecase

interface SigninUseCase {
    fun signin(command: SigninCommand): SigninInfo

    data class SigninCommand(
        val username: String,
        var password: String,
    )

    data class SigninInfo(
        val username: String,
        val token: String
    )
}