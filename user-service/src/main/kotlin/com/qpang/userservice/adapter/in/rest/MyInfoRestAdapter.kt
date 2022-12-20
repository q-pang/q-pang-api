package com.qpang.userservice.adapter.`in`.rest

import com.qpang.userservice.adapter.`in`.rest.dto.PaymentMethodResponseDto
import com.qpang.userservice.application.port.`in`.usecase.MyInfoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class MyInfoRestAdapter(
    private val myInfoUseCase: MyInfoUseCase
) {
    @GetMapping("/user/my")
    fun myInfo(
        @RequestHeader(name = "username", required = true) username: String
    ): ResponseEntity<MyInfoResponseDto> {
        val command = MyInfoUseCase.MyInfoCommand(username)
        return ResponseEntity.ok().body(MyInfoResponseDto.from(myInfoUseCase.command(command)))
    }

    data class MyInfoResponseDto(
        val id: String,
        val username: String,
        val name: String,
        val paymentMethods: List<PaymentMethodResponseDto>
    ) {
        companion object {
            fun from(info: MyInfoUseCase.MyInfoInfo) = MyInfoResponseDto(
                id = info.id,
                username = info.username,
                name = info.name,
                paymentMethods = info.paymentMethods.map {
                    PaymentMethodResponseDto(
                        id = it.id,
                        type = it.type,
                        company = it.company,
                        number = it.number
                    )
                }
            )
        }
    }
}