package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.RegisterPaymentMethodUseCase
import com.qpang.userservice.domain.PaymentMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class RegisterPaymentMethodAdapter(
    private val registerPaymentMethodUseCase: RegisterPaymentMethodUseCase
) {
    @PostMapping("/user/payment-method")
    fun registerPaymentMethod(
        @RequestBody @Valid dto: RegisterPaymentMethodRequestDto,
        @RequestHeader(name = "username", required = true) username: String
    ): ResponseEntity<RegisterPaymentMethodResponseDto> {
        val command = RegisterPaymentMethodUseCase.RegisterPaymentMethodCommand(
            username = username,
            type = dto.type,
            company = dto.company
        )
        return ResponseEntity(
            RegisterPaymentMethodResponseDto.from(registerPaymentMethodUseCase.command(command)),
            HttpStatus.CREATED
        )
    }

    data class RegisterPaymentMethodRequestDto(
        val type: PaymentMethod.PaymentMethodType,
        val company: PaymentMethod.CardCompany
    )

    data class RegisterPaymentMethodResponseDto(
        val id: String,
        val username: String,
        val type: PaymentMethod.PaymentMethodType,
        val company: PaymentMethod.CardCompany
    ) {
        companion object {
            fun from(info: RegisterPaymentMethodUseCase.RegisterPaymentMethodInfo) = RegisterPaymentMethodResponseDto(
                id = info.id,
                username = info.username,
                type = info.type,
                company = info.company
            )
        }
    }
}