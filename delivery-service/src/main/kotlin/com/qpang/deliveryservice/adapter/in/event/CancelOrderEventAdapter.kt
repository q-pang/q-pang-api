package com.qpang.deliveryservice.adapter.`in`.event

import com.qpang.deliveryservice.adapter.`in`.event.dto.CancelOrderEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CancelOrderEventAdapter(
    private val cancelDeliveryByOrderIdUseCase: CancelDeliveryByOrderIdUseCase
) {
    @KafkaListener(topics = ["cancel-order"])
    fun consume(event: CancelOrderEvent) {
        cancelDeliveryUseCase.command(CancelDeliveryByOrderIdUseCase.CancelDeliveryByOrderIdCommand(event.orderId))
    }
}