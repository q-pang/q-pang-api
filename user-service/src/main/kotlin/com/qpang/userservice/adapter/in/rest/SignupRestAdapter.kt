package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.SignupUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
class SignupRestAdapter(
    private val signupUseCase: SignupUseCase
) {
    @PostMapping("/user/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(@RequestBody @Valid dto: SignupRequestDto): SignupResponseDto =
        SignupResponseDto.from(signupUseCase.signup(dto.toCommand()))

    data class SignupRequestDto(val name: String) {
        fun toCommand(): SignupUseCase.SignupCommand = SignupUseCase.SignupCommand(name = this.name)
    }

    data class SignupResponseDto(
        val id: UUID,
        val name: String
    ) {
        companion object {
            fun from(info: SignupUseCase.SignupInfo) = SignupResponseDto(
                id = info.id,
                name = info.name
            )
        }
    }
}