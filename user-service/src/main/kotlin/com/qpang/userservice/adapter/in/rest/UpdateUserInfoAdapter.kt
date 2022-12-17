package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.UpdateUserInfoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
class UpdateUserInfoAdapter(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) {
    @PatchMapping("/user/update-info")
    fun updateUserInfo(@RequestBody @Valid dto: UpdateUserInfoRequestDto): ResponseEntity<UpdateUserInfoResponseDto> =
        ResponseEntity.ok().body(UpdateUserInfoResponseDto.from(updateUserInfoUseCase.command(dto.toCommand())))

    data class UpdateUserInfoRequestDto(
        val id: String,
        val name: String,
    ) {
        fun toCommand(): UpdateUserInfoUseCase.UpdateUserCommand = UpdateUserInfoUseCase.UpdateUserCommand(
            id = this.id,
            name = this.name
        )
    }

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