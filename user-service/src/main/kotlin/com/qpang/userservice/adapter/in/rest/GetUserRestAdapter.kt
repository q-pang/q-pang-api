package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.GetUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetUserRestAdapter(
    private val getUserUseCase: GetUserUseCase
) {
    @GetMapping("/user/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<GetUserResponseDto> {
        val command = GetUserUseCase.GetUserCommand(id)
        return ResponseEntity.ok().body(GetUserResponseDto.from(getUserUseCase.command(command)))
    }

    data class GetUserResponseDto(
        val id: String,
        val username: String,
        val name: String
    ) {
        companion object {
            fun from(info: GetUserUseCase.GetUserInfo) = GetUserResponseDto(
                id = info.id,
                username = info.username,
                name = info.name
            )
        }
    }
}