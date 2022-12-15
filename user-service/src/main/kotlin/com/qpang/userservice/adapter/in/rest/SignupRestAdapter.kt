package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.SignupUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignupRestAdapter(
    private val signupUseCase: SignupUseCase
) {
    @PostMapping("/user/signup")
    fun signup(@RequestBody dto: SignupDto) {
        signupUseCase.signup(dto.toCommand())
    }

    data class SignupDto(val name: String) {
        fun toCommand(): SignupUseCase.SignupCommand = SignupUseCase.SignupCommand(name = this.name)
    }
}