package com.qpang.deliveryservice.adapter.`in`.event

import com.qpang.deliveryservice.adapter.`in`.event.dto.OrderEvent
import com.qpang.deliveryservice.application.port.`in`.RegisterDeliveryUseCase
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderEventAdapter(
    private val registerDeliveryUseCase: RegisterDeliveryUseCase
) {
    @KafkaListener(topics = ["order"])
    fun consume(event: OrderEvent) {
        registerDeliveryUseCase.command(RegisterDeliveryUseCase.RegisterDeliveryCommand(event.orderId))
    }
}