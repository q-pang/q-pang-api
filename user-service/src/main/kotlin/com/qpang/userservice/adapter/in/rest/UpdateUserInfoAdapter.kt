package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.UpdateUserInfoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
class UpdateUserInfoAdapter(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) {
    @PatchMapping("/user/update")
    fun updateUserInfo(
        @RequestBody @Valid dto: UpdateUserInfoRequestDto,
        @RequestHeader(name = "username", required = true) username: String
    ): ResponseEntity<UpdateUserInfoResponseDto> {
        val command = UpdateUserInfoUseCase.UpdateUserCommand(username = username, name = dto.name)
        return ResponseEntity.ok().body(UpdateUserInfoResponseDto.from(updateUserInfoUseCase.command(command)))
    }

    data class UpdateUserInfoRequestDto(val name: String)

    data class UpdateUserInfoResponseDto(
        val id: String,
        val username: String,
        val name: String
    ) {
        companion object {
            fun from(info: UpdateUserInfoUseCase.UpdateUserInfo) = UpdateUserInfoResponseDto(
                id = info.id,
                username = info.username,
                name = info.name
            )
        }
    }
}