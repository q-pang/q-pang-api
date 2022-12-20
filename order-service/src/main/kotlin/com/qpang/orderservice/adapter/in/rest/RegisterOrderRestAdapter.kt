package com.qpang.orderservice.adapter.`in`.rest

import com.qpang.orderservice.adapter.`in`.rest.dto.OrderItemRequestDto
import com.qpang.orderservice.adapter.`in`.rest.dto.OrderItemResponseDto
import com.qpang.orderservice.adapter.`in`.rest.dto.PaymentRequestDto
import com.qpang.orderservice.adapter.`in`.rest.dto.PaymentResponseDto
import com.qpang.orderservice.application.port.`in`.RegisterOrderUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class RegisterOrderRestAdapter(
    private val registerOrderUseCase: RegisterOrderUseCase
) {
    @PostMapping("/order")
    fun registerOrder(@RequestBody @Valid dto: RegisterOrderRequestDto): ResponseEntity<RegisterOrderResponseDto> =
        ResponseEntity(RegisterOrderResponseDto.from(registerOrderUseCase.command(dto.toCommand())), HttpStatus.CREATED)

    data class RegisterOrderRequestDto(
        val consumerId: String,
        val orderItemRequsetDtos: List<OrderItemRequestDto>,
        val paymentRequestDto: PaymentRequestDto
    ) {
        fun toCommand(): RegisterOrderUseCase.RegisterOrderCommand = RegisterOrderUseCase.RegisterOrderCommand(
            consumerId = consumerId,
            orderItemCommands = orderItemRequsetDtos.map { it.toCommand() },
            paymentCommand = paymentRequestDto.toCommand()
        )
    }

    data class RegisterOrderResponseDto(
        val totalPrice: Long,
        val consumerId: String,
        val orderItemResponseDtos: List<OrderItemResponseDto>,
        val paymentResponseDto: PaymentResponseDto
    ) {
        companion object {
            fun from(info: RegisterOrderUseCase.RegisterOrderInfo): RegisterOrderResponseDto = RegisterOrderResponseDto(
                totalPrice = info.totalPrice,
                consumerId = info.consumerId,
                orderItemResponseDtos = info.orderItemInfos.map { OrderItemResponseDto.from(it) },
                paymentResponseDto = PaymentResponseDto.from(info.paymentInfo)
            )
        }
    }
}