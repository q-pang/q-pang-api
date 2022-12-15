package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.SigninUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
class SigninRestAdapter(
    private val signinUseCase: SigninUseCase
) {
    @PostMapping("/user/signin")
    fun signin(@RequestBody @Valid dto: SigninRequestDto): ResponseEntity<SigninResponseDto> =
        ResponseEntity.ok().body(SigninResponseDto.from(signinUseCase.signin(dto.toCommand())))

    data class SigninRequestDto(
        val username: String,
        val password: String,
    ) {
        fun toCommand(): SigninUseCase.SigninCommand = SigninUseCase.SigninCommand(
            username = this.username,
            password = this.password
        )
    }

    data class SigninResponseDto(
        val username: String,
        val token: String
    ) {
        companion object {
            fun from(info: SigninUseCase.SigninInfo) = SigninResponseDto(
                username = info.username,
                token = info.token
            )
        }
    }
}