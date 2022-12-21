package com.qpang.orderservice.adapter.`in`.rest

import com.qpang.orderservice.adapter.`in`.rest.dto.OrderItemResponseDto
import com.qpang.orderservice.adapter.`in`.rest.dto.PaymentResponseDto
import com.qpang.orderservice.application.port.`in`.CancelOrderUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class CancelOrderRestAdapter(
    private val cancelOrderUseCase: CancelOrderUseCase
) {
    @DeleteMapping("/order")
    fun cancelOrder(@RequestBody @Valid dto: CancelOrderRequestDto): ResponseEntity<CancelOrderResponseDto> =
        ResponseEntity.ok().body(CancelOrderResponseDto.from(cancelOrderUseCase.command(dto.toCommand())))

    data class CancelOrderRequestDto(
        val orderId: String
    ) {
        fun toCommand(): CancelOrderUseCase.CancelOrderCommand = CancelOrderUseCase.CancelOrderCommand(
            orderId = orderId
        )
    }

    data class CancelOrderResponseDto(
        val orderId: String,
        val totalPrice: Long,
        val consumerId: String,
        val orderItemResponseDtos: List<OrderItemResponseDto>,
        val paymentResponseDto: PaymentResponseDto
    ) {
        companion object {
            fun from(info: CancelOrderUseCase.CancelOrderInfo): CancelOrderResponseDto = CancelOrderResponseDto(
                orderId = info.orderId,
                totalPrice = info.totalPrice,
                consumerId = info.consumerId,
                orderItemResponseDtos = info.orderItemInfos.map { OrderItemResponseDto.from(it) },
                paymentResponseDto = PaymentResponseDto.from(info.paymentInfo)
            )
        }
    }
}