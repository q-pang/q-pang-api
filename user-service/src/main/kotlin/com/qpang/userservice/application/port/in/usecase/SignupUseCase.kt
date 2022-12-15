package com.qpang.userservice.application.port.`in`.usecase

import com.qpang.userservice.domain.User

interface SignupUseCase {
    fun signup(command: SignupCommand)

    data class SignupCommand(val name: String) {
        fun toEntity(): User = User(name = this.name)
    }
}