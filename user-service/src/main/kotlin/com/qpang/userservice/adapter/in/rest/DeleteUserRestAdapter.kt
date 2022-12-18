package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.DeleteUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class DeleteUserRestAdapter(
    private val deleteUserUseCase: DeleteUserUseCase
) {
    @DeleteMapping("/user")
    fun deleteUser(
        @RequestHeader(name = "username", required = true) username: String
    ): ResponseEntity<DeleteUserResponseDto> {
        val command = DeleteUserUseCase.DeleteUserCommand(username)
        return ResponseEntity.ok().body(DeleteUserResponseDto.from(deleteUserUseCase.command(command)))
    }

    data class DeleteUserResponseDto(
        val username: String,
        val name: String
    ) {
        companion object {
            fun from(info: DeleteUserUseCase.DeleteUserInfo) = DeleteUserResponseDto(
                username = info.username,
                name = info.name
            )
        }
    }
}