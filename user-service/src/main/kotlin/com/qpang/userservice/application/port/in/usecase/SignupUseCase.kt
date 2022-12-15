package com.qpang.userservice.application.port.`in`.usecase

import com.qpang.userservice.domain.User
import java.util.*

interface SignupUseCase {
    fun signup(command: SignupCommand): SignupInfo

    data class SignupCommand(val name: String) {
        fun toEntity(): User = User(name = this.name)
    }

    data class SignupInfo(
        val id: UUID,
        val name: String
    ) {
        companion object {
            fun from(user: User) = SignupInfo(
                id = user.getId(),
                name = user.name
            )
        }
    }
}