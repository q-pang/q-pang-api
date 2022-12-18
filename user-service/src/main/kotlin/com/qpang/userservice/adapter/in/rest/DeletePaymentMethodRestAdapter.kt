package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.application.port.`in`.usecase.DeletePaymentMethodUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class DeletePaymentMethodRestAdapter(
    private val deletePaymentMethodUseCase: DeletePaymentMethodUseCase
) {
    @DeleteMapping("/user/payment-method")
    fun registerPaymentMethod(
        @RequestBody @Valid dto: DeletePaymentMethodRequestDto,
        @RequestHeader(name = "username", required = true) username: String
    ): ResponseEntity<DeletePaymentMethodResponseDto> {
        val command = DeletePaymentMethodUseCase.DeletePaymentMethodCommand(
            username = username,
            paymentMethodId = dto.paymentMethodId
        )
        return ResponseEntity.ok()
            .body(DeletePaymentMethodResponseDto.from(deletePaymentMethodUseCase.command(command)))
    }

    data class DeletePaymentMethodRequestDto(
        val paymentMethodId: String
    )

    data class DeletePaymentMethodResponseDto(
        val id: String,
        val username: String
    ) {
        companion object {
            fun from(info: DeletePaymentMethodUseCase.DeletePaymentMethodInfo) = DeletePaymentMethodResponseDto(
                id = info.id,
                username = info.username
            )
        }
    }
}