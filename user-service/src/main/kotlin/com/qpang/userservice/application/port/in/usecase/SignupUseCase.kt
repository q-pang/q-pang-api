package com.qpang.userservice.application.port.`in`.usecase

import com.qpang.userservice.domain.User
import java.util.*

interface SignupUseCase {
    fun signup(command: SignupCommand): SignupInfo

    data class SignupCommand(
        val username: String,
        var password: String,
        val name: String
    ) {
        fun toEntity(): User = User(
            username = this.username,
            password = this.password,
            name = this.name
        )
    }

    data class SignupInfo(
        val id: UUID,
        val username: String,
        val name: String
    ) {
        companion object {
            fun from(user: User) = SignupInfo(
                id = user.getId(),
                username = user.username,
                name = user.name
            )
        }
    }
}